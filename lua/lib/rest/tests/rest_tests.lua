--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 22/04/17
-- Time: 14:43
-- To change this template use File | Settings | File Templates.
--
package.path = package.path..";../?.lua"


local Rest = require("rest")

describe("Defining method and url for call", function()

    it("will fail because no url was passed to get", function ()
        local myRest = Rest()
        assert.has.errors(function()
            myRest:get()
        end)
        assert.has.errors(function()
            myRest:get("")
        end)
    end)

    it("will generate a get/post method and url", function ()
        local myRest = Rest()
        local url = "http://api.github.com"
        local result = myRest:get(url)
        assert.are.equals("get", result.options.method)
        assert.are.equals(url, result.options.url)

        local result = myRest:post(url)
        assert.are.equals("post", result.options.method)
        assert.are.equals(url, result.options.url)
    end)
end)

describe("Calling a native rest", function()

    it("will fail because there is no callback passed", function ()
        local myRest = Rest()
        assert.has.errors(function()

            myRest:get("http://api.globo.com"):asJson()
        end)
    end)

    it("will fail because there is no method and url", function ()
        local myRest = Rest()
        assert.has.errors(function()
            local myCallBack = function() end
            myRest:asJson(myCallBack)
        end)
    end)
end)