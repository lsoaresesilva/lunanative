--
-- Created by IntelliJ IDEA.
-- User: macbookair
-- Date: 30/03/17
-- Time: 11:44
-- To change this template use File | Settings | File Templates.
--

local button = {

}

local class = require("30log")
local Button = class("Button", {options={}})

--- Cria um botão. 
-- O botão pode ser criado apenas com texto ou com imagem.
-- No momento, ao criar um botão com imagem é possível personalizar sua aparência apenas com uma imagem. No futuro será implementado para utilizar até duas imagens, representando os estados em que o botão está sendo pressionado e quando não está sendo pressionado.
-- A imagem deve estar localizada na pasta 'img' e ser informada com a sua respectiva extensão. Apenas os formatos PNG e JPG são suportados.
-- Exemplo para um botão com texto apenas:
-- local Button = require("button")
-- local myButtonProperties = {txt="Hello world!"}
-- local myTextualButton = Button:newButton(txt)
-- Exemplo para um botão com imagem:
-- local Button = require("button")
-- local myButtonProperties = {img={normal="hello.png"}
-- @param opções (tabela) Definições sobre a aparência do botão.  É uma tabela com as seguintes chaves: 
-- txt (string) representa o texto que será exibido no botão.
-- img (tabela) deve indicar o arquivo de imagem a ser utilizado no botão. Deve-se utilizar a chave "normal", cujo valor será o nome do arquivo de imagem.
-- @return Retorna uma instância de Button.
function Button:init(options)
	self._nativeObject = ButtonFactory:newButton("lua", options)
	if self._nativeObject == nil then
		error("button creation failed.")
	end
end

--[[function Button:newButton(options)
    local newButton = {}
    setmetatable(newButton, {__index=button})
    newButton._nativeObject = ButtonFactory:newButton("lua", options)
    if newButton._nativeObject == nil then
    	error("button creation failed.")
    end
    return newButton
end]]

--- Define uma função que será chamado quando o botão for clicado.
-- @param callback (função) nome da função que deve ser chamada quando o botão for clicado.
function Button:setTouchCallback(callback)
    self._nativeObject:setTouchCallback(callback)
end

return Button

