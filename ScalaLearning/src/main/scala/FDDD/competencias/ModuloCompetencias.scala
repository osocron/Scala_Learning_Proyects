package FDDD.competencias


trait ModuloCompetencias {

  type Competition

  type CompetitionInfo

  type Participant

  type ParticipantInfo

  type Record[A]

  def createParticipant: ParticipantInfo => Participant

  def createCompetition: CompetitionInfo => List[Participant] => Competition
  
  def recordCompeticion: Competition => Record[Competition]

}
