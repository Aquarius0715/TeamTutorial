package aquarius0715.debugplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.*

class DebugPlugin : JavaPlugin() {

    private val prefix = "[debugPlugin]"
    lateinit var manager: ScoreboardManager
    lateinit var board: Scoreboard
    lateinit var objective: Objective

    override fun onEnable() {

        manager = Bukkit.getScoreboardManager()
        board = manager.newScoreboard
        objective = board.registerNewObjective("BlueMonster", "Dummy")

        getCommand("debug")!!.setExecutor(this)

    }

    override fun onDisable() {

        // Plugin shutdown logic
    }

    private fun createTeams(player: Player) {

        objective.displayName = "BlueMonster"
        objective.displaySlot = DisplaySlot.SIDEBAR

        val blueMonster = board.registerNewTeam("blueMonster")
        val hiroshi = board.registerNewTeam("Hiroshi")

        blueMonster.prefix = "${ChatColor.BLUE}[青鬼]"
        hiroshi.prefix = "[ひろし]"

        for (team in board.teams) {

            player.sendMessage("${prefix}Team: ${team.displayName} を作成しました。")

        }

    }

    private fun countTeams(player: Player) {

        player.sendMessage("${prefix}チームの総数は${board.teams.size}です。")

    }

    private fun showTeams(player: Player) {

        player.sendMessage("${prefix}チーム一覧")

        for (team in board.teams) {

            player.sendMessage(team.displayName)

        }

    }

    private fun deleteTeam(player: Player, teamName: String) {

        for (team in board.teams) {

            if (team.displayName == teamName) {

                board.teams.remove(team)

                player.sendMessage("${prefix}Team: ${team.displayName} を削除しました。")

                return

            }

        }

        player.sendMessage("${prefix}そのような名称のチームは存在しません。")

    }

    private fun deleteAllTeams(player: Player) {

        board = manager.newScoreboard

        player.sendMessage("${prefix}チームを全削除しました。")

    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender !is Player) return false

        when (label) {

            "debug" -> {

                when (args.size) {

                    1 -> {

                        when (args[0]) {

                            "createteam" -> {

                                createTeams(sender)

                                return true

                            }

                            "countteam" -> {

                                countTeams(sender)

                                return true

                            }

                            "showteams" -> {

                                showTeams(sender)

                                return true

                            }

                            "deleteteams" -> {

                                deleteAllTeams(sender)

                            }

                        }

                    }

                    2 -> {

                        when (args[0]) {

                            "deleteteam" -> {

                                deleteTeam(sender, args[1])

                            }

                        }

                    }

                }

            }

        }

        return false
    }

}