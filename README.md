# PetPhraseX

A lightweight client-side mod that automatically adds customizable "pet phrases" (like "nya~") to your chat messages. 
一款轻量级的用户端模组，能为您发出的聊天讯息自动加上自定义的「口癖」（例如 " nya~"）。

[![Modrinth](https://img.shields.io/badge/Modrinth-PetPhraseX-00AF5C?logo=modrinth)](https://modrinth.com/mod/petphrasex)
[![CurseForge](https://img.shields.io/badge/CurseForge-PetPhraseX-F16436?logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/petphrasex)

*[<img src="https://www.mcmod.cn/static/public/images/logo.png?v=4" height="16" alt="MCMod Logo"> 最大的MInecraft中文MOD百科](https://www.mcmod.cn/class/16747.html)*

## ✨ 最新功能 (v1.4.0) / Features

本模组目前已完全同步 **Fabric** 与 **NeoForge** 双端的功能，并带来以下进阶文字处理能力：

* **🎲 随机口癖 (Random Phrases)**
    支援在设定档中使用分号（`;`）分隔多个口癖，每次发送讯息时将自动随机抽取一个使用！（若需输入真实的分号，请使用 `;;` 进行转义）。
* **✂️ 短句分割处理 (Sentence Processing)**
    系统会自动根据标点符号与空格识别短句，您可以选择将口癖加在整段讯息的头尾，或是精确地加在每一个短句的头尾。
* **🚫 忽略标记 (Ignore Mark)**
    预设为 `#`。当您发送的讯息以此标记开头时（例如 `#请问...`），该条讯息将**不会**被添加任何口癖，且发送时会自动隐藏该标记。
* **⚙️ 自定义介面 (Customizable UI)**
    支援游戏内设定（Fabric 需搭配 ModMenu），提供 5 种独立设定：
    * `Ignore Mark` (忽略标记)
    * `Message Prefix` (整段消息前缀)
    * `Message Suffix` (整段消息后缀)
    * `Sentence Prefix` (短句前缀)
    * `Sentence Suffix` (短句后缀)

## 📦 支援版本 / Supported Versions
* **Minecraft**: `1.21` ~ `1.21.11`
* **Mod Loaders**: Fabric & NeoForge
