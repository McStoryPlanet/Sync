# McStoryPlanet Sync Plugin

##### Ein Plugin, womit man Daten speichern und (not implemented) mit einer Datenbank syncronisieren kann. 
##### Da es kein Cache hat, macht es nur selten Probleme

## Usage
### Create a Data Storage
| Class     | Create/Get methode        | Use                                                   |
|-----------|---------------------------|-------------------------------------------------------|
| Economy   | getEconomy(String name)   | Save the currency                                     |
| LevelData | getLevelData(String name) | Save the level                                        |
| IntData   | getData(String name)      | Save a integer Not recommended for currency and level |

#### Example  
    Economy mines = Economy.getEconomy("mines")

### Modify Datas  
Every data storage type had their own methodes. 
In every data storage exist the get???() and set???() methode
To get/modify the data, you need a UUID.

#### Example  
    Economy mines = Economy.getEconomy("mines")  
    // GET  
    int currentMines = mines.getEconomy(player.getUniqueId())  
    //SET  
    mines.setEconomy(player.getUniqueId(), 60)  
