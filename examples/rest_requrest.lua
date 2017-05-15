local Rest = require("rest")

function myCallback(event)
    print(event.code)
    print(event.content)
end

Rest:get("https://reqres.in/api/users?page=2"):asJson(myCallback)











