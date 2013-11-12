{
		"_comment": "IMPORTANT : go to the wiki page to know about options configuration https://github.com/simogeo/Filemanager/wiki/Filemanager-configuration-file",
    "options": {
        "culture": "fr",
        "lang": "play",
        "defaultViewMode": "grid",
        "autoload": true,
        "showFullPath": false,
        "browseOnly": false,
        "showConfirmation": true,
        "showThumbs": true,
        "generateThumbnails": true,
        "searchBox": true,
        "listFiles": true,
        "fileSorting": "default",
        "chars_only_latin": true,
        "dateFormat": "d M Y H:i",
        "serverRoot": true,
        "fileRoot": "/",
        "relPath": "/image?name=/",
        "logger": false,
        "capabilities": ["select", "download", "rename", "move", "delete"],
        "plugins": []
    },
    "security": {
        "uploadPolicy": "ALLOW_ALL",
        "uploadRestrictions": [
        ]
    },
    "upload": {
        "overwrite": false,
        "imagesOnly": false,
        "fileSizeLimit": 16
    },
    "exclude": {
        "unallowed_files": [
            ".htaccess"
        ],
        "unallowed_dirs": [
            "_thumbs",
            ".CDN_ACCESS_LOGS",
            "cloudservers"
        ],
        "unallowed_files_REGEXP": "/^\\./uis",
        "unallowed_dirs_REGEXP": "/^\\./uis"
    },
    "images": {
        "imagesExt": [
            "jpg",
            "jpeg",
            "gif",
            "png",
            "svg"
        ]
    },
    "videos": {
        "showVideoPlayer": true,
        "videosExt": [
            "ogv",
            "mp4",
            "webm"
        ],
        "videosPlayerWidth": 400,
        "videosPlayerHeight": 222
    },
    "audios": {
        "showAudioPlayer": true,
        "audiosExt": [
            "ogg",
            "mp3",
            "wav"
        ]
    },
    "extras": {
        "extra_js": [],
        "extra_js_async": true
    },
    "icons": {
        "path": "images/fileicons/",
        "directory": "_Open.png",
        "default": "default.png"
    }
}