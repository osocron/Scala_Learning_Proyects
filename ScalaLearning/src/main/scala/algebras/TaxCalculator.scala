package algebras

/**
  * Created by osocron on 9/01/17.
  */
trait TaxCalculator {

  sealed trait TaxType
  case object Tax extends TaxType
  case object Fee extends TaxType
  case object Commission extends TaxType

  sealed trait TransactionType
  case object InterestComputation extends TransactionType
  case object dividend extends TransactionType

  type Amount = BigDecimal
  case class Balance(amount: Amount = 0)

  trait TaxCalculationTable {
    type T <: TransactionType
    val transactionType: T

    def getTaxRates: Map[TaxType, Amount] = {
      ???
    }
  }

  trait TaxCalculation {
    type S <: TaxCalculationTable
    val table: S

    def calculate(taxOn: Amount): Amount = {
      table.getTaxRates.map { case (_, r) =>
        doCompute(taxOn, r)
      }.sum
    }

    protected def doCompute(taxOn: Amount, rate: Amount): Amount = taxOn * rate

  }

  trait SingaporeTaxCalculation extends TaxCalculation {
    def calculateGST(tax: Amount, gstRate: Amount): Amount = tax * gstRate
  }

  trait InterestCalculation {
    type C <: TaxCalculation
    val taxCalculation: C

    def interest(b: Balance): Option[Amount] = Some(b.amount * 0.05)

    def calculate(balance: Balance): Option[Amount] =
      interest(balance).map(i => i - taxCalculation.calculate(i))

  }

  //This are the instances to the modules

  object InterestTaxCalculationTable extends TaxCalculationTable {
    override type T = TransactionType
    override val transactionType = InterestComputation
  }

  object TaxCalculation extends TaxCalculation {
    override type S = TaxCalculationTable
    override val table = InterestTaxCalculationTable
  }

  object InterestCalculation extends InterestCalculation {
    override type C = TaxCalculation
    override val taxCalculation = TaxCalculation
  }

}
