class = require("lib.external.30log")

local Observable = class("Observable", {observers={}})

function Observable:attach(callback)
    if type(callback) == "function" then
        table.insert(self.observers, callback)
    else
        error("Callback must be a function.")
    end
end

function Observable:notify(event)

    for i=1, #self.observers do
        print("chamou")
        local func = self.observers[i]
        func(event)
    end
end

return Observable