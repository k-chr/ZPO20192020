def checkBalancedParens(exp: String): Boolean = {
  exp.toCharArray.foldLeft(0) {
    case (a, ')' | '(') if a < 0 => -1
    case (a, ')') => a - 1
    case (a, '(') => a + 1
    case (a, _) => a + 0
  } == 0
}

assert(!checkBalancedParens("))(("))
assert(checkBalancedParens("(()(ab)c)"))
assert(!checkBalancedParens("((()())"))
assert(!checkBalancedParens("(()))("))
assert(checkBalancedParens("(aa(b)()c)d"))