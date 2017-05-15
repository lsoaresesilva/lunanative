--package.path = package.path..";/lib/rest/?.lua"
package.path = package.path..";lib/external/?.lua"
package.path = package.path..";lib/userinterface/?.lua"
package.path = package.path..";lib/rest/?.lua"
package.path = package.path..";lib/error/?.lua"
package.path = package.path..";scripts/?.lua"

--require("error_handling")


if require("main") ~= true then
    error("rest_use.lua not found. you must create one it is the entry point for your application.")
end
