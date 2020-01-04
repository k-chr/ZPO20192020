package Kamil215691.ZPO.LAB11.Zad4
object Passwd {
  private def preValidatePasswd(min: Int)( max: Int)( passwd: String) :Boolean = {
      min <= passwd.length && max >= passwd.length && hasAtLeastTwoNumbers(passwd) && hasCapitalLetter(passwd) && hasSmallLetter(passwd) && hasSpecialCharacter(passwd)
  }
  private def hasSmallLetter(passwd: String): Boolean = {
      passwd.filter(ch => ch >= 'a' && ch <= 'z').length > 0
  }
  private def hasCapitalLetter(passwd: String): Boolean = {
    passwd.filter(ch => ch >= 'A' && ch <= 'Z').length > 0
  }
  private def hasSpecialCharacter(passwd: String): Boolean = {
    passwd.filter(ch => !ch.isLetterOrDigit).length > 0
  }
  private def hasAtLeastTwoNumbers(passwd: String): Boolean = {
    passwd.filter(ch => ch.isDigit).length > 1
  }
  private val helper1 = preValidatePasswd(8)(32)_

  def validate(passwd: String):Boolean= helper1(passwd)

  def main(args: Array[String] ) = {
    assert(!validate("1234567890"))
    assert(!validate("admin"))
    assert(validate("12Aw!@adfss"))
    assert(!validate("!@#%^&*"))
    assert(validate("qWeR67*9)"))
    assert(!validate("             "))
  }
}

