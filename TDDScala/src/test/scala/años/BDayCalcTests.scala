package años

import org.scalatest.FlatSpec

/**
  * Created by osocron on 4/03/16.
  */
class BDayCalcTests extends FlatSpec{

  "The BdayCalculator" should "calculate year of birth given a persons age" in {
    assertResult(1990) {
      BDayCalc.calculateYearOfBirth(26)
    }
  }

}
