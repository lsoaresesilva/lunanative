-- https://reqres.in
local class = require("30log")

--- Realiza requisições do tipo REST.
-- As requisições podem ser realizadas para os métodos GET, POST, PUT e DELETE.
-- Caso a requisição retorne um valor o mesmo deve ser no formato jSON.
-- Exemplo de requisição GET:
-- local Rest = require("rest")
-- local function myCallback(response)
-- print(responde.code)
-- print(response.response)
-- end
-- Rest:get("http://jsonplaceholder.typicode.com/posts"):asJson(myCallback)
local Rest = class("Rest", {options={}})

-- This attribute is used to save a reference to self.
-- It is needed in iOS because when calling the readHttpResponse does not pass any parameter.
-- As we need a reference to self, we save it before use to use in readHttpResponse.
local currentInstance

function Rest:_validateURL(url)
    if url == nil or type(url) ~= "string" or url == "" then
        error("A not empty url string must be passed.")
    end

    return true
end

function Rest:_prepare(method, url, params)
    if self:_validateURL(url) then
        self.options.method = method
        self.options.url = url
        self.options.params = params
        return self
    end

    return nil
end

--- Define a URL que será usada na requisição GET
-- @param url (string) URL em que a requisição será feita.
function Rest:get(url)
    return self:_prepare("GET", url)
end

--- Define a URL que será usada na requisição DELETE
-- @param url (string) URL em que a requisição será feita.
function Rest:delete(url)
return self:_prepare("DELETE", url)
end

--- Define a URL que será usada na requisição POST
-- @param url (string) URL em que a requisição será feita.
function Rest:post(url, params)
    return self:_prepare("POST", url, params)
end

--- Define a URL que será usada na requisição PUT
-- @param url (string) URL em que a requisição será feita.
function Rest:put(url, params)
    return self:_prepare("PUT", url, params)
end

--- Define a função que será chamada ao receber um retorno da URL.
-- @param callback (function) função que será chamada quando o servidor retornar. Esta função receberá um parâmetro do tipo tabela contendo o código da resposta e a resposta, nas respectivas chaves: code e response.
function Rest:asJson(callback)
    if self.options.method == nil or self.options.url == nil then
        error("Did you missed to call get() or post()?")
    end

    if callback == nil or type(callback) ~= "function" then
        error("You must pass a callback.")
    end

    self.options.userCallback = callback
    self.options.callback = readHttpResponse
    self._nativeObject = RestFactory:create("lua")
    currentInstance = self
    self._nativeObject:makeRequest(self.options)
end

-- This function is called by native Rest implementation when a Http response is ready.
-- Will call user's callback for rest passing a string response as parameter.
-- Should not be called directly.
-- @return void.
function readHttpResponse()

    local restInstance = currentInstance
    currentInstance = nil -- reset its states
    local result = restInstance._nativeObject:response()
    if result ~= nil then
        local response = {}
        response.content = result:getStringResponse()
        response.code = result:getResponseCode()
        restInstance.options.userCallback(response)
    end
end

return Rest

