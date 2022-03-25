package net.sakuragame.eternal.kirrajoiner

import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI
import org.bukkit.entity.Player
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.Table
import taboolib.module.database.getHost

object Database {

    private val host = KirraJoiner.conf.getHost("settings.database")

    private val tableQuest = Table("quest_points", host) {
        add("uid") {
            type(ColumnTypeSQL.INT) {
                options(ColumnOptionSQL.UNIQUE_KEY, ColumnOptionSQL.NOTNULL)
            }
        }
        add("category") {
            type(ColumnTypeSQL.VARCHAR, 64) {
                options(ColumnOptionSQL.UNIQUE_KEY)
            }
        }
        add("count") {
            type(ColumnTypeSQL.INT)
        }
    }

    private val dataSource by lazy {
        ClientManagerAPI.getDataManager().dataSource
    }

    init {
        tableQuest.createTable(dataSource)
    }

    fun getCount(player: Player, category: String): Int? {
        val uid = ClientManagerAPI.getUserID(player.uniqueId)
        if (uid == -1) return null
        return tableQuest.select(dataSource) {
            where("uid" eq uid and ("category" eq category))
        }.firstOrNull {
            getInt("count")
        }
    }
}