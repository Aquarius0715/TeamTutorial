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

    private fun createTeams() {

        objective.displayName = "BlueMonster"
        objective.displaySlot = DisplaySlot.SIDEBAR

        val blueMonster = board.registerNewTeam("blueMonster")
        val hiroshi = board.registerNewTeam("Hiroshi")

        blueMonster.prefix = "${ChatColor.BLUE}[青鬼]"
        hiroshi.prefix = "[ひろし]"

        Bukkit.broadcastMessage("${prefix}Teamの作成に成功！")

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

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender !is Player) return false

        when (label) {

            "debug" -> {

                when (args[0]) {

                    "createteam" -> {

                        createTeams()

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

                }

            }

        }

        return false
    }

}