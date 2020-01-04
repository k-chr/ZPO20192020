import scala.util.Try

var isInt = (i: String)=>Try(i.toInt).isSuccess
var str = "-3 + 4 - 1 + 1 + 2 - 5 + 6"
var sum : Int = 0
var op: String = ""
var splitted = str.split(" ")
var opFun = (operand: Int ) => {
  op match {
    case "-" => sum - operand
    case "+" => sum + operand
    case _ => operand
  }
}

for (str1 <- splitted){
  str1 match {
    case "+" | "-" => op = str1
    case number if isInt(number) => sum = opFun(number.toInt)
    case _ => throw new RuntimeException("Cannot parse provided values!")
  }
}
Console.println(s"Expression: $str = $sum")