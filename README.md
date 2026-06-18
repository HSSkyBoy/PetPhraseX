# PetPhraseX

A lightweight client-side mod that automatically adds customizable "pet phrases" (like "nya~") to your chat messages. 
一款轻量级的用户端模组，能为您发出的聊天讯息自动加上自定义的「口癖」（例如 " nya~"）。

[![Modrinth](https://img.shields.io/badge/Modrinth-PetPhraseX-00AF5C?logo=modrinth)](https://modrinth.com/mod/petphrasex)
[![CurseForge](https://img.shields.io/badge/CurseForge-PetPhraseX-F16436?logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/petphrasex)

*[<img src="https://www.mcmod.cn/static/public/images/logo.png?v=4" height="16" alt="MCMod Logo"> 最大的MInecraft中文MOD百科](https://www.mcmod.cn/class/16747.html)*

## ✨ 最新功能 / Features

### 基础功能
以下功能属于 PetPhraseX 的日常使用能力，一般用户端即可使用，不强依赖 `Fabric API`：

* **🎲 随机口癖**
  同一个栏位可用分号（`;`）分隔多个候选内容，送出讯息时会随机抽取一个使用；若要输入真正的分号，可用 `;;` 转义。
* **✂️ 整句与短句双层加工**
  可分别设定整段讯息的 `prefix` / `suffix`，也可再针对短句设定 `sentence_prefix` / `sentence_suffix`。
* **🚫 忽略标记**
  预设为 `#`。当讯息以忽略标记开头时，该讯息不会套用任何口癖，送出时也会自动移除标记本身。
* **⚙️ 游戏内设定**
  支援直接在游戏内调整 `ignore_mark`、`prefix`、`suffix`、`sentence_prefix`、`sentence_suffix` 五个栏位。

### 服务端额外功能
以下功能属于 **Minecraft 26.1+ Fabric** 伺服器的额外能力，需搭配 `Fabric API` 才会启用：

* **🛡️ 伺服器条件式口癖处理**
  伺服器可依照既有条件规则，在玩家送出讯息时套用服务端口癖逻辑。
* **👮 管理员可强制指定单一玩家口癖**
  可透过 `/ppx force <玩家> ...` 管理每位玩家的独立口癖设定。
* **🔄 玩家可切回自己的本地设定**
  若玩家端也安装本模组，当伺服器对其启用强制口癖时，仍可在设定画面中解除伺服器强制并改回使用本地设定。

## 📦 目前支援的版本 / Supported Versions
* **Fabric**: `1.21.x` - `26.2`
* **NeoForge**: `1.21.x`

## 🕹️ 指令用法 / Commands
伺服器管理功能的主要指令别名为 `/ppx`，`/petphrasex` 也同样可用。

* 查看某位玩家目前的强制设定：
  ` /ppx force <玩家> view `
* 清除某位玩家的强制设定：
  ` /ppx force <玩家> clear `
* 设定某个栏位的强制口癖：
  ` /ppx force <玩家> set <栏位> <内容> `

可用栏位：

* `ignore_mark`
* `prefix`
* `suffix`
* `sentence_prefix`
* `sentence_suffix`

范例：

```mcfunction
/ppx force NkBe set suffix 喵～
/ppx force NkBe set prefix [管理员指定]
/ppx force NkBe set sentence_suffix 呢
/ppx force NkBe view
/ppx force NkBe clear
```

## 🧩 Fabric API 说明
用户端的基础口癖功能不强依赖 `Fabric API`。

只有以下伺服器相关功能需要 `Fabric API` 才会启用：

* `/ppx` / `/petphrasex` 强制口癖指令
* 伺服器对玩家的强制口癖同步
* 玩家在设定画面中解除伺服器强制并改用本地设定

如果没有安装 `Fabric API`，模组仍可正常载入，但上述伺服器功能不会生效。

### 服务端支援范围
目前这套伺服器端强制口癖功能，仅在 **Minecraft 26.1+ 的 Fabric** 环境提供支援。

也就是说：

* 一般用户端本地口癖功能仍可独立使用
* `/ppx` / `/petphrasex` 伺服器管理指令与强制同步功能，目前只针对 **26.1+ Fabric 伺服器** 维护与测试
