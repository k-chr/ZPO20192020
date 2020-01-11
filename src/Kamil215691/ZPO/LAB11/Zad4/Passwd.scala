package Kamil215691.ZPO.LAB11.Zad4

object Passwd {

  private def maxLength(max: Int) = (str: String) => max >= str.length

  private def minLength(min: Int) = (str: String) => min <= str.length

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

  private val helper1 =(passwd :String, list: List[String=>Boolean]) => list.forall(l => l(passwd))

  def validate(passwd: String):Boolean = helper1(passwd, List(hasSmallLetter, hasCapitalLetter, hasSpecialCharacter, hasAtLeastTwoNumbers, maxLength(32), minLength(8)))

  def main(args: Array[String] ) = {
    assert(!validate("1234567890"))
    assert(!validate("admin"))
    assert(validate("12Aw!@adfss"))
    assert(!validate("!@#%^&*"))
    assert(validate("qWeR67*9)"))
    assert(!validate("             "))
  }
}

