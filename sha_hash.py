#Sender alice
# Python 3 code to demonstrate SHA hash algorithms.
import hashlib
# initializing string
str = "hello how r u"

print("Message is: " + str)
print ("\r")

result = hashlib.sha512(str.encode())

# printing the equivalent hexadecimal value.
print("The hexadecimal equivalent of SHA512 is : ")
print(result.hexdigest())
print ("\r")
