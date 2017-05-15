-- https://reqres.in
local class = require("30log")

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

function Rest:get(url)
    return self:_prepare("GET", url)
end

function Rest:delete(url)
return self:_prepare("DELETE", url)
end

function Rest:post(url, params)
    return self:_prepare("POST", url, params)
end

function Rest:put(url, params)
    return self:_prepare("PUT", url, params)
end

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

