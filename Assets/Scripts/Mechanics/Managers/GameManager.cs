using JetBrains.Annotations;
using TMPro;
using UnityEngine;


/// <summary>
/// 全局游戏管理者
/// </summary>
public class GameManager : MonoBehaviour
{
    public Turn currentTurn;

    public TimeController timeCtrl;

    public UnitManager unitManager;

    public DiceManager diceManager;

    public TextMeshProUGUI turnText;

    public void Awake()
    {
        currentTurn = Turn.START_TURN;
        UpdateText();
    }

    // 当回合结束时
    public void OnTurnOver()
    {
        bool isSuccess = JudgeGameSuccess();
        bool isFail = JudgeGameFail();

        if (isFail)
        {
            // 失败动画展示并进入结算画面
        }
        if (isSuccess)
        {
            // 胜利动画展示并进入结算画面
        }

        // 传递消息
        diceManager.OnTurnOver(currentTurn);

        // 游戏没有结束，继续下个回合
        NextTurn();

        // 响应下个回合开始
        OnTurnUp();
    }

    // 当回合开始时
    public void OnTurnUp()
    {
        diceManager.OnTurnUp(currentTurn);
        ResetTurnTime();
        UpdateText();
    }

    // 重置回合时间
    public void ResetTurnTime()
    {
        switch (currentTurn)
        {
            case Turn.START_TURN:
                timeCtrl.ResetTime(1);
                break;
            case Turn.PLAYER_TURN:
                timeCtrl.ResetTime(20);
                break;
            case Turn.ENEMY_TURN:
                timeCtrl.ResetTime(10);
                break;
            case Turn.END_TURN:
                timeCtrl.ResetTime(1);
                break;
        }
    }

    // 判断游戏是否胜利或者失败
    public bool JudgeGameSuccess()
    {
        return true;
    }

    public bool JudgeGameFail()
    {
        return true;
    }

    public void NextTurn()
    {
        switch (currentTurn)
        {
            case Turn.START_TURN:
                currentTurn = Turn.PLAYER_TURN;
                break;
            case Turn.PLAYER_TURN:
                currentTurn = Turn.ENEMY_TURN;
                break;
            case Turn.ENEMY_TURN:
                currentTurn = Turn.END_TURN;
                break;
            case Turn.END_TURN:
                currentTurn = Turn.START_TURN;
                break;
        }
    }

    void UpdateText()
    {
        turnText.text = "当前回合： " + currentTurn;
    }

    void Update()
    {

    }


}