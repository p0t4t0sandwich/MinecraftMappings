# Minecraft Mappings

This "tool" creates transitive mappings for various Minecraft versions.

All the mappings can be translated to one another for a given version: `spigot2mcp.srg`, `srg2obf.mcp`, etc.

Supported versions:

|        | Mojang | MCP | Searge | Spigot | Yarn | Intermediary | Legacy Intermediary |
|--------|--------|-----|--------|--------|------|--------------|---------------------|
| 1.21.6 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.21.5 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.21.4 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.21.3 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.21.2 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.21.1 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.21   | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.20.6 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.20.5 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.20.4 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.20.3 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.20.2 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.20.1 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.20   | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.19.4 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.19.3 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.19.2 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.19.1 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.19   | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.18.2 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.18.1 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.18   | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.17.1 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.17   | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.16.5 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.16.4 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.16.3 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.16.2 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.16.1 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.16   | ✅      | ❌   | ✅      | ❌      | ✅    | ✅            | ❌                   |
| 1.15.2 | ✅      | ❌   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.15.1 | ✅      | ✅   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.15   | ✅      | ✅   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.14.4 | ✅      | ✅   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.14.3 | ❌      | ✅   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.14.2 | ❌      | ✅   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.14.1 | ❌      | ✅   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.14   | ❌      | ✅   | ✅      | ✅      | ✅    | ✅            | ❌                   |
| 1.13.2 | ❌      | ✅   | ✅      | ✅      | ❌    | ❌            | ✅                   |
| 1.13.1 | ❌      | ✅   | ✅      | ✅      | ❌    | ❌            | ❌                   |
| 1.13   | ❌      | ✅   | ✅      | ✅      | ❌    | ❌            | ❌                   |
| 1.12.2 | ❌      | ✅   | ✅      | ✅      | ❌    | ❌            | ✅                   |
| 1.12.1 | ❌      | ❌   | ❌      | ✅      | ❌    | ❌            | ❌                   |
| 1.12   | ❌      | ✅   | ✅      | ✅      | ❌    | ❌            | ❌                   |
| 1.11.2 | ❌      | ❌   | ❌      | ✅      | ❌    | ❌            | ✅                   |
| 1.11.1 | ❌      | ❌   | ❌      | ✅      | ❌    | ❌            | ❌                   |
| 1.11   | ❌      | ✅   | ✅      | ✅      | ❌    | ❌            | ❌                   |
| 1.10.2 | ❌      | ✅   | ✅      | ✅      | ❌    | ❌            | ✅                   |
| 1.10.1 | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ❌                   |
| 1.10   | ❌      | ❌   | ❌      | ✅      | ❌    | ❌            | ❌                   |
| 1.9.4  | ❌      | ✅   | ✅      | ✅      | ❌    | ❌            | ✅                   |
| 1.9.3  | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ❌                   |
| 1.9.2  | ❌      | ❌   | ❌      | ✅      | ❌    | ❌            | ❌                   |
| 1.9.1  | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ❌                   |
| 1.9    | ❌      | ❌   | ❌      | ✅      | ❌    | ❌            | ❌                   |
| 1.8.9  | ❌      | ✅   | ✅      | ❌      | ❌    | ❌            | ✅                   |
| 1.8.8  | ❌      | ✅   | ✅      | ✅      | ❌    | ❌            | ✅                   |
| 1.8.7  | ❌      | ❌   | ❌      | ✅      | ❌    | ❌            | ✅                   |
| 1.8.6  | ❌      | ❌   | ❌      | ✅      | ❌    | ❌            | ✅                   |
| 1.8.5  | ❌      | ❌   | ❌      | ✅      | ❌    | ❌            | ✅                   |
| 1.8.4  | ❌      | ❌   | ❌      | ✅      | ❌    | ❌            | ✅                   |
| 1.8.3  | ❌      | ❌   | ❌      | ✅      | ❌    | ❌            | ✅                   |
| 1.8.2  | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ✅                   |
| 1.8.1  | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ✅                   |
| 1.8    | ❌      | ✅   | ✅      | ✅      | ❌    | ❌            | ✅                   |
| 1.7.10 | ❌      | ✅   | ✅      | ❌      | ❌    | ❌            | ✅                   |
| 1.7.9  | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ✅                   |
| 1.7.8  | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ✅                   |
| 1.7.6  | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ✅                   |
| 1.7.5  | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ✅                   |
| 1.7.4  | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ✅                   |
| 1.7.3  | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ✅                   |
| 1.7.2  | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ✅                   |
| 1.7.1  | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ✅                   |
| 1.7    | ❌      | ❌   | ❌      | ❌      | ❌    | ❌            | ✅                   |

Supported formats:

- SRG
- CSRG
- TSRG
- Tiny <small>(fields descriptors don't exist)</small>
- JSON

These mappings were made possible by @Techcable, the MCP team, Bukkit, SpigotMC, and various other people.

## TODO

- [ ] [older versions](https://github.com/agaricusb/MinecraftRemapping)
- [ ] < 1.8 CraftBukkit mappings?
- [ ] snapshots?

## License

* All Kotlin scripts are MIT Licensed.

* The MCP mappings are the property of the MCP Team and are released under the MCP License.

* The Spigot mappings are copyright SpigotMC Pty. Ltd.

* The Yarn mappings are licensed under the Creative Commons Zero license.

* The Mojang mappings are copyright Microsoft.
