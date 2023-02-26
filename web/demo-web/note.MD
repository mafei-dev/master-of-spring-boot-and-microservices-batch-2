## request mapping

example-1 : localhost:8080/hi/1 | hi
example-1 : localhost:8080/hi/2 | {msg : hi hi}
example-1 : localhost:8080/hi/3 | Hi im from index1.html

GET example-2 : localhost:8080/user/{username} | {username : mafei, age : 12}
POST example-2 : localhost:8080/user/add/{username : mafei, age : 12} | RESPONSE_CODE
PUT example-2 : localhost:8080/user/update | {username : mafei, age : 15} | RESPONSE_CODE
DELETE example-2 : localhost:8080/user/delete/{username} | RESPONSE_CODE

op-1 > localhost:8080/user/delete/mafei (PathVariable)
op-2 > localhost:8080/user/delete?username=mafei&isActive=true


