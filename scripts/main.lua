local Rest = require("rest")

function handleResponse(response)
    print(response.code)
end

Rest:get("http://jsonplaceholder.typicode.com/posts"):asJson(handleResponse)
