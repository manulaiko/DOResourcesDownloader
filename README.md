# DOResourcesDownloader
DarkOrbit Resources Downloader

Usage:
```bash
java -jar bin/dord.jar [args] [path_to_download]
```

If `path_to_download` isn't set, resources will be saved to the current working directory.

Available arguments:

 * `-a` - `--all`: Downloads all resources.
 * `-x` - `--xml`: Downloads only xml files.
 * `-s` - `--swf`: Downloads only swf files.
 * `-2` - `--2d`: Downloads only 2D client files.
 * `-3` - `--3d`: Downloads only 3D client files.
 * `-i` - `--img`: Downloads only `do_img`.

All the parameters can be combined.

Examples:
```bash
java -jar bin/dord.jar --swf --2d --3d ~/DarkOrbit

java -jar bin/dord.jar --img ~/DarkOrbit

java -jar bin/dord.jar --swf --xml ~/DarkOrbit
```
