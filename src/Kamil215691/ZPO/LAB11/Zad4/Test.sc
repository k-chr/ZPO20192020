import Kamil215691.ZPO.LAB11.Zad4.Passwd.validate

assert(!validate("1234567890"))
assert(!validate("admin"))
assert(validate("12Aw!@adfss"))
assert(!validate("!@#%^&*"))
assert(validate("qWeR67*9)"))
assert(!validate("             "))
