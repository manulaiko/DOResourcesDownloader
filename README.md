# DOResourcesDownloader
DarkOrbit Resources Downloader.

Usage:
```bash
java -jar bin/dord.jar [args] [proxy (host:port)] [path_to_download]
```


If `path_to_download` isn't set, resources will be saved to the current working directory.

Available arguments:
 * `-d` - `--debug`: Enables debug mode (prints all files being downloaded).
 * `-a` - `--all`: Downloads all resources (default if no other argument is specified).
 * `-x` - `--xml`: Downloads only xml files.
 * `-s` - `--swf`: Downloads only swf files.
 * `-2` - `--2d`: Downloads only 2D client files.
 * `-3` - `--3d`: Downloads only 3D client files.
 * `-i` - `--img`: Downloads only `do_img`.
 * `-l` - `--loadingScreenAssets`: Downloads only loading screen assets.
 * `-h` - `--host`: Host to download files from (default `test2.darkorbit.bigpoint.com`).
 * `-o` - `--overwrite`: Overwrites already downloaded files (by default already downloaded files will be skipped).

All the parameters can be combined.

Examples:
```bash
java -jar bin/dord.jar --swf --2d --3d ~/DarkOrbit

java -jar bin/dord.jar --img ~/DarkOrbit

java -jar bin/dord.jar --swf --xml ~/DarkOrbit

java -jar bin/dord.jar -h test3.darkorbit.bigpoint.com

java -jar bin/dord.jar -h test3.darkorbit.bigpoint.com -o

java -jar bin/dord.jar 192.168.0.100:8080

java -jar bin/dord.jar
```
