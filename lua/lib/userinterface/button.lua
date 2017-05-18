--- Representa um botão clicável.
local class = require("30log")
local Button = class("Button", {options={}})

--- Cria um botão. 
-- O botão pode ser criado com texto ou imagem.
-- Para utilizar imagens, ela deve estar localizada na pasta 'img' e ser informada com a sua respectiva extensão. Apenas os formatos PNG e JPG são suportados.
-- É possível utilizar até duas imagens no botão, uma para representar o estado quando o botão não está clicado e outra para representar a aparência do botão sob clique.
-- Exemplo para um botão com texto apenas:
-- local Button = require("button")
-- local myTextualButton = Button:newButton({text="Hello world!"})
-- Exemplo para um botão com imagem:
-- local Button = require("button")
-- local myButtonProperties = {img={normal="aimage.png", pressed="pressedimage.png"}
-- @param opções (tabela) Definições sobre a aparência do botão.  É uma tabela com as seguintes chaves: 
-- text (string) representa o texto que será exibido no botão.
-- size (float) determina o tamanho da fonte usada no texto do botão.
-- img (tabela) deve indicar o arquivo de imagem a ser utilizado no botão. É possível representar os estados do botão (normal) ou (pressed) utilizando estas referências como chave para esta tabela. O valor destas chaves deve ser o nome do arquivo de imagem, devendo estas estarem na pasta 'img'.
-- @return Retorna uma instância de Button.
function Button:init(options)
	self._nativeObject = ButtonFactory:newButton("lua", options)
	if self._nativeObject == nil then
		error("button creation failed.")
	end
end

--- Define qual função  será chamada quando o botão for clicado.
-- @param callback (função) nome da função que deve ser chamada quando o botão for clicado.
function Button:setTouchCallback(callback)
    self._nativeObject:setTouchCallback(callback)
end

return Button

