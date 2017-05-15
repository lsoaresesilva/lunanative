package.path = package.path..";../?.lua"
package.path = package.path..";../../external/?.lua"
package.path = package.path..";../../../models/?.lua"
package.path = package.path..";../../util/?.lua"

require("model")

local rawStub = {
    count = function()
        return 0
    end,
    get = function(pos)
        return {
            getAllKeys = function()
                return {self, self, self, self}
            end
        }
    end
}

describe("Creating models from SQL results", function()
    
    it("Should fail, because there is no sql passed as parameter", function()
          assert.has.errors(function() 
              Model:buildModels()
              end, "Cannot generate models without SQL rows.")
    end)

    it("Should fail, because there an invalid raw sql was passed", function()
        assert.has.errors(function()
            Model:buildModels({})
        end, "Cannot generate models without a valid raw sql.")
    end)

    it("Should return zero models as there were no sql results", function()

        local result = #Model:buildModels(rawStub)
        assert.equals(0, result)

    end)
    

end)