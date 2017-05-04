--package.path = package.path..";/lib/rest/?.lua"
package.path = package.path..";lib/external/?.lua"
package.path = package.path..";lib/userinterface/?.lua"


if require("main") ~= true then
    error("main.lua not found. you must create one it is the entry point for your application.")
end
