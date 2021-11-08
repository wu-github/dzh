# electron打包
需要先设置环境变量：

    set ELECTRON_MIRROR=https://cdn.npm.taobao.org/dist/electron/

# electron-packager
    icon=public/image/favicon.ico

# electron-builder
    "build": {
        "productName":"xxxx",//项目名，生成的exe文件的前缀名
        "appId": "xxxxx",//包名  
        "copyright":"xxxx",//版权信息
        "compression": "store", // "store" | "normal"| "maximum" 打包压缩情况(store 相对较快)，store 39749kb, maximum 39186kb
        "directories": {
            "output": "electron-app" // 输出文件夹
        }, 
        "asar": false, // asar打包
        "files"[
            "electron",
            "!node_modeules/**/*"    
        ],
        "extraResources":  {
            "from": "./build",
            "to": "build"
        },
        "win": {  
            "icon": "build/icons/icon.ico",//图标路径
            "target": [
                {
                    "target": "nsis",
                    "arch": [
                        "ia32"
                    ]
                }
            ]
        },
        "nsis": {
            "oneClick": false, // 一键安装
            "guid": "xxxx", //注册表名字，不推荐修改
            "perMachine": true, // 是否开启安装时权限限制（此电脑或当前用户）
            "allowElevation": true, // 允许请求提升。 如果为false，则用户必须使用提升的权限重新启动安装程序。
            "allowToChangeInstallationDirectory": true, // 允许修改安装目录
            "installerIcon": "./build/icons/aaa.ico", // 安装图标
            "uninstallerIcon": "./build/icons/bbb.ico", //卸载图标
            "installerHeaderIcon": "./build/icons/aaa.ico", // 安装时头部图标
            "createDesktopShortcut": true, // 创建桌面图标
            "createStartMenuShortcut": true, // 创建开始菜单图标
            "shortcutName": "xxxx" // 图标名称
        }
    }