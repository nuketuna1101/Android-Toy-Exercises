package com.example.rps

import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rps.R
import com.example.rps.databinding.ActivityIngameBinding
import kotlinx.android.synthetic.main.activity_ingame.*

class Ingame : AppCompatActivity() {

    // advantage :: 현재 공격권 상태 / matchresult :: 일련의 과정 이후 가위바위보 승부 판정
    // 공격권을 의미 :: 0 - 초기화 상태(공격권 아무도 없음), 1 - player 공격권, 2 - opponenet 공격권
    var advantage : Int = 0
    var matchresult : Int = 0
    // player와 opponent의 승패 판정을 위한 심볼 변수 :: 1 - rock / 2 - paper / 3 - scissor
    var symbol_player : Int = 0
    var symbol_opponent : Int = 0
    // opponent의 경우 random하게 결정되기에, 난수 생성위한 범위 설정
    val randomrange = (1..3)

    // 디테일 추가용
    var matches : Int = 0
    var wins : Int = 0
    var winrate : Float = 0.0f


    // 인게임 초기 화면 출력
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingame)
        // 게임 시작시 토스트 메시지
        Toast.makeText(this@Ingame, "Game Start!", Toast.LENGTH_SHORT).show()
        // 하단 3가지 버튼 클릭 시 이벤트
        imgbutton_rock.setOnClickListener {
            Log.d("ko", "click event - rock")
            // player와 opponent 심볼 결정
            symbol_player = 1
            logicRPS(symbol_player)
        }
        imgbutton_paper.setOnClickListener {
            Log.d("ko", "click event - paper")
            // player와 opponent 심볼 결정
            symbol_player = 2
            logicRPS(symbol_player)
        }
        imgbutton_scissor.setOnClickListener {
            Log.d("ko", "click event - scissor")
            // player와 opponent 심볼 결정
            symbol_player = 3
            logicRPS(symbol_player)
        }
    }

    // HOWTO 화면의 메뉴 나오는 부분 코드
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_mainscreen, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // gobacktomain 메뉴버튼으로 메인 화면으로 복귀하기
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.gobacktomain -> {
            finish()
            true
        }
        else -> true
    }

    // eventdrived :: imagebutton 3개 중 하나를 누름에 따라서 발생


    // 일반 메소드들 선언

    fun printPlayerSymbol(symbolcode:Int){
        if (symbolcode == 1){
            player_rock.visibility = View.VISIBLE
            player_paper.visibility = View.INVISIBLE
            player_scissor.visibility = View.INVISIBLE
        } else if (symbolcode == 2){
            player_rock.visibility = View.INVISIBLE
            player_paper.visibility = View.VISIBLE
            player_scissor.visibility = View.INVISIBLE
        } else if (symbolcode == 3){
            player_rock.visibility = View.INVISIBLE
            player_paper.visibility = View.INVISIBLE
            player_scissor.visibility = View.VISIBLE
        } else {
            Log.d("ko", "ERROR - Player Symbol Code")
        }
    }

    fun printOpponentSymbol(symbolcode:Int){
        if (symbolcode == 1){
            opponent_rock.visibility = View.VISIBLE
            opponent_paper.visibility = View.INVISIBLE
            opponent_scissor.visibility = View.INVISIBLE
        } else if (symbolcode == 2){
            opponent_rock.visibility = View.INVISIBLE
            opponent_paper.visibility = View.VISIBLE
            opponent_scissor.visibility = View.INVISIBLE
        } else if (symbolcode == 3){
            opponent_rock.visibility = View.INVISIBLE
            opponent_paper.visibility = View.INVISIBLE
            opponent_scissor.visibility = View.VISIBLE
        } else {
            Log.d("ko", "ERROR - Opponent Symbol Code")
        }
    }

    fun decideRPS(symbol_player:Int, symbol_opponent:Int):Int{
        if (symbol_player == 1){
            if(symbol_opponent == 1)
                return 0
            else if (symbol_opponent == 2)
                return 2
            else if (symbol_opponent == 3)
                return 1
        }
        else if (symbol_player == 2){
            if(symbol_opponent == 1)
                return 1
            else if (symbol_opponent == 2)
                return 0
            else if (symbol_opponent == 3)
                return 2
        }
        else if (symbol_player == 3) {
            if (symbol_opponent == 1)
                return 2
            else if (symbol_opponent == 2)
                return 1
            else if (symbol_opponent == 3)
                return 0
        }
        return -1
    }

    fun printWhoAdv(matchresult:Int){
        when (matchresult){
            0 -> Log.d("ko", "match result : no one in advance")
            1 -> Log.d("ko", "match result : player in advantage")
            2 -> Log.d("ko", "match result : opponent gets advantage")
            else -> Log.d("ko", "Error")
        }
    }

    fun logicRPS(symbol_player:Int){
        symbol_opponent = randomrange.random()
        Log.d("ko", "player symbolcode : " + symbol_player)
        Log.d("ko", "opponent symbolcode : " + symbol_opponent)

        // 매칭 이전에 누가 선공권을 가지고 있는가
        printWhoAdv(advantage)

        // 두 심볼에 대해서 가위바위보 매칭 결과
        matchresult = decideRPS(symbol_player, symbol_opponent)

        // 현재 공격권의 상태에 따라서 분기
        if (advantage == 0){
            // 초기 상태 :: 공격권가진 사람 없음. 매치 결과를 통해 공격권 부여하는 단계
            if (matchresult == 0) {
                // 무승부이므로 아무런 업데이트 없다.
                Log.d("ko", "match draw :: Must repeat Rock-paper-scissor")
            } else if (matchresult == 1 || matchresult == 2) {
                // 매칭결과에 따라 공격권 업데이트
                printWhoAdv(matchresult)
                advantage = matchresult
            }
        } else if (advantage == 1){
            // player 공격권 가진 상태.
            if (matchresult == 0) {
                // 가위바위보 결과 무승부, 즉 player 승리
                Log.d("ko", "match completed : Player Win!")
                Toast.makeText(this@Ingame, "Congratulation! You Win!", Toast.LENGTH_SHORT).show()
                updateInfos(1, 1)
                // 승부가 끝났으므로 공격권 초기화
                advantage = 0
                matchresult = 0
                // 진동
                eventVib()
            } else if (matchresult == 1 || matchresult == 2) {
                // 매칭결과에 따라 공격권 업데이트
                printWhoAdv(matchresult)
                advantage = matchresult
            }
        } else if (advantage == 2){
            // opponent 공격권 가진 상태.
            if (matchresult == 0) {
                // 가위바위보 결과 무승부, 즉 opponent 승리
                Log.d("ko", "match completed : Player Lose!")
                Toast.makeText(this@Ingame, "Try Better.. You Lose T^T", Toast.LENGTH_SHORT).show()
                updateInfos(1, 0)
                // 승부가 끝났으므로 공격권 초기화
                advantage = 0
                matchresult = 0
                // 진동
                eventVib()
            } else if (matchresult == 1 || matchresult == 2) {
                // 매칭결과에 따라 공격권 업데이트
                printWhoAdv(matchresult)
                advantage = matchresult
            }
        }
        // 화면 출력
        printPlayerSymbol(symbol_player)
        printOpponentSymbol(symbol_opponent)
    }

    // 라운드, 승수, 승률 업데이트
    fun updateInfos(addMatches:Int, addWins:Int){
        matches += addMatches
        var round = matches + 1
        wins += addWins
        winrate = (wins/matches).toFloat()
        Log.d("ko", "Infos Updated :: round : " + round + "Wins / Matches : " + wins + " / " + matches)
        text_round.setText("Round : " + round)
        text_win.setText("Wins / Matches : " + wins + " / " + matches)
    }


    fun eventVib(){
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
    }


}

