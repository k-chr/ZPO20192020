import scala.None

def checkBalancedParens(exp: String): Boolean= {
  val arr = exp.toCharArray
  val rV = arr.foldLeft(0){
    case (a, ')' | '(') if (a < 0) => -1
    case (a, ')') => a - 1
    case (a, '(') => a + 1
    case (a, c) => a + 0
  }
  rV == 0
}
assert(!checkBalancedParens("))(("))
assert(checkBalancedParens("(()(ab)c)"))
assert(!checkBalancedParens("((()())"))
assert(!checkBalancedParens("(()))("))
assert(checkBalancedParens("(aa(b)()c)d"))