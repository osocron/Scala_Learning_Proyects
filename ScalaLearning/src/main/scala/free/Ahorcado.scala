package free

import cats._
import cats.free._
import freasymonad.cats.free

import scala.io.StdIn

case class Juego(fraseSecreta: FraseSecreta, tableroUsuario: TableroUsuario, muerto: Muerto)
case class EstadoJuego(continua: Boolean, ganaste: Boolean)
case class FraseSecreta(frase: String)
case class TableroUsuario(tablero: String)
case class Muerto(vidas: Int)
case class Letra(letra: Char)


@free trait Ahorcado {

  type AhorcadoF[A] = Free[ADT, A]
  sealed trait ADT[A]

  def generarMuerto(): AhorcadoF[Muerto]
  def mostrarMuerto(m: Muerto): AhorcadoF[Unit]
  def generarFraseSecreta(): AhorcadoF[FraseSecreta]
  def generarTableroUsuario(fraseSecreta: FraseSecreta): AhorcadoF[TableroUsuario]
  def mostrarTableroUsuario(tableroUsuario: TableroUsuario): AhorcadoF[Unit]

  def empezarJuego(): AhorcadoF[Juego] =
    for {
      muerto <- generarMuerto()
      frase <- generarFraseSecreta()
      tableroUsuario <- generarTableroUsuario(frase)
      _ <- mostrarTableroUsuario(tableroUsuario)
      _ <- mostrarMuerto(muerto)
    } yield Juego(frase, tableroUsuario, muerto)

  def pedirLetraAUsuario(): AhorcadoF[Letra]
  def hayCoincidencias(l: Letra, f: FraseSecreta): AhorcadoF[Boolean]
  def mostrarCoincidencias(l: Letra, f: FraseSecreta, t: TableroUsuario): AhorcadoF[TableroUsuario]
  def matarAlMuerto(b: Boolean, m: Muerto): AhorcadoF[Muerto]

  def ronda(fraseSecreta: FraseSecreta,
            tableroUsuario: TableroUsuario,
            muerto: Muerto): AhorcadoF[Juego] = {
    for {
      letra <- pedirLetraAUsuario()
      coincidencia <- hayCoincidencias(letra, fraseSecreta)
      tablero <- mostrarCoincidencias(letra, fraseSecreta, tableroUsuario)
      masMuerto <- matarAlMuerto(coincidencia, muerto)
    } yield Juego(fraseSecreta, tablero, masMuerto)
  }

  def evaluarRonda(t: TableroUsuario, m: Muerto): AhorcadoF[EstadoJuego]
  def mostrarFin(b: Boolean): AhorcadoF[Unit]
}

trait Program {

  import Ahorcado.ops._

  def jugar(fraseSecreta: FraseSecreta,
            tableroUsuario: TableroUsuario,
            muerto: Muerto,
            continua: Boolean,
            ganaste: Boolean): AhorcadoF[Boolean] = {
    if (!continua)
      for {
        estado <- evaluarRonda(tableroUsuario, muerto)
      } yield estado.ganaste
    else
      for {
        juego <- ronda(fraseSecreta, tableroUsuario, muerto)
        estado <- evaluarRonda(juego.tableroUsuario, juego.muerto)
        ganaste <- jugar(juego.fraseSecreta, juego.tableroUsuario, juego.muerto, estado.continua, estado.ganaste)
      } yield ganaste
  }

  val program: AhorcadoF[Unit] =
    for {
      juego <- empezarJuego()
      ganaste <- jugar(juego.fraseSecreta, juego.tableroUsuario, juego.muerto, continua = true, ganaste = false)
      _ <- mostrarFin(ganaste)
    } yield ()


}

object ConsoleInterpreter extends App with Program {


  val consoleInterpreter = new Ahorcado.Interp[Id] {

    override def generarMuerto(): Id[Muerto] = Muerto(6)

    override def mostrarMuerto(m: Muerto): Id[Unit] = println("\nVidas = " + m.vidas)

    override def generarFraseSecreta(): Id[FraseSecreta] = FraseSecreta("EJEMPLO")

    override def generarTableroUsuario(fraseSecreta: FraseSecreta): Id[TableroUsuario] =
      TableroUsuario(fraseSecreta.frase.map(c => '_'))

    override def mostrarTableroUsuario(tableroUsuario: TableroUsuario): Id[Unit] =
      println(s"Bienvenido! \n ${tableroUsuario.tablero}")

    override def pedirLetraAUsuario(): Id[Letra] = {
      println("\nAdivina una letra!")
      Letra(StdIn.readLine().head)
    }

    override def hayCoincidencias(l: Letra, f: FraseSecreta): Id[Boolean] = {
      val coincidencias = f.frase.exists(c => c == l.letra)
      println(s"\nHay coincidencias? : $coincidencias")
      coincidencias
    }

    override def mostrarCoincidencias(l: Letra, f: FraseSecreta, t: TableroUsuario): Id[TableroUsuario] = {
      val posiciones = f.frase.map { c =>
        if (c == l.letra) true else false
      }
      val nuevoTablero = t.tablero.zip(posiciones).map { case (c, b) =>
        if (b) l.letra else c
      }
      val tablero = nuevoTablero.foldLeft("")((acc, c) => acc + c.toString)
      println(s"\n Asi esta la cosa: \n$tablero")
      TableroUsuario(tablero)
    }

    override def matarAlMuerto(b: Boolean, m: Muerto): Id[Muerto] = {
      if (!b) {
        println(s"\nTienes : ${m.vidas - 1} vidas!")
        Muerto(m.vidas - 1)
      } else m
    }

    override def evaluarRonda(t: TableroUsuario, m: Muerto): EstadoJuego = {
      if (t.tablero.forall(c => c != '_')) EstadoJuego(continua = false, ganaste = true)
      else if (m.vidas == 0) EstadoJuego(continua = false, ganaste = false)
      else EstadoJuego(continua = true, ganaste = false)
    }

    override def mostrarFin(b: Boolean): Id[Unit] =
      if (b) println("Felicidades! Ganaste!")
      else println("Hijoles, perdiste de nuevo!")
    
  }

  consoleInterpreter.run(program)

}
