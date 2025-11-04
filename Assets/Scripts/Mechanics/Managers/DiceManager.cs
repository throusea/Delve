using System;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.InputSystem;
using UnityEngine.UI;

/// <summary>
/// 管理骰子的类。
/// </summary>
[RequireComponent(typeof(CombinationChecker))]
public class DiceManager : MonoBehaviour
{
    public Action<List<int>> OnDiceSelectionChangedEvent;

    private DelveControls controls;

    private readonly int maxRollNum = 3;

    public int availableRollNum = 3;

    public TextMeshProUGUI availableRollText;

    public static int diceNum = 6;
    public DiceUI[] allDiceUI = new DiceUI[diceNum];
    
    private List<DiceUI> selectedDiceUIs = new();


    void Awake()
    {
        controls = new DelveControls();
    }
    void OnEnable()
    {
        controls.GamePlay.RollDice.performed += OnRollDice;
        controls.Enable();
    }

    private void OnDisable()  
    {  
        controls.GamePlay.RollDice.performed -= OnRollDice; 
        controls.Disable(); // 禁用输入控制  
    }
    public void OnRollDice(InputAction.CallbackContext context)
    {
        RollAllDices();
    }

    public void ResetDiceState()
    {
        foreach (DiceUI diceUI in allDiceUI)
        {
            diceUI.SetHighlight(false);
        }

        // 更新骰子状态传递事件
        selectedDiceUIs.Clear();
    }

    // 重新投掷骰子
    public void ReRollDices()
    {
        ResetDiceState();
        OnDiceSelectionChanged();

        availableRollNum = maxRollNum;
        RollAllDices();
    }
    
    // 投掷所有未选中的骰子
    public void RollUnSelectedDices()
    {
        if (availableRollNum > 0)
        {
            for (int i = 0; i < diceNum; i++)
            {
                if (!allDiceUI[i].isHighlight())
                {
                    int die = UnityEngine.Random.Range(1, 7); // Random number between 1 and 6
                    allDiceUI[i].SetValue(die);
                }
            }

            availableRollNum--;
            UpdateAvialiableText();

            Debug.Log("Dice Rolled");
        }
        else
        {
            Debug.Log("You have no chance to roll!");
        }
    }

    // 投掷骰子
    public void RollAllDices()
    {
        RollUnSelectedDices();
    }

    public List<DiceUI> GetHighlightDiceUIs()
    {
        return selectedDiceUIs;
    }

    // 当骰子被选中状态发生变化时触发
    public void OnDiceSelectionChanged()
    {
        // var checker = GetComponent<CombinationChecker>();

        List<int> dices = new();
        // DiceUI -> int
        foreach (DiceUI diceUI in selectedDiceUIs)
        {
            dices.Add(diceUI.value);
        }

        // 检查当前技能组合
        // var skill = checker.CheckCombination(dices);

        // 广播事件
        NotifySelectionChanged(dices);
    }

    // 更新可选UI
    public void UpdateAvialiableText()
    {
        availableRollText.text = $"剩余投掷次数： {availableRollNum}";
    }

    public void OnTurnUp(Turn turn)
    {
        switch (turn)
        {
            case Turn.START_TURN:
                availableRollNum = maxRollNum;
                break;
            case Turn.PLAYER_TURN:
                // 投掷按钮设置为可用
                // TODO
                break;
            case Turn.ENEMY_TURN:
                // 投掷按钮这只为不可用
                break;
            case Turn.END_TURN:
                break;
        }
    }

    public void OnTurnOver(Turn turn)
    {
        ResetDiceState();
    }
    
    public void NotifySelectionChanged(List<int> dices)
    {
        OnDiceSelectionChangedEvent?.Invoke(dices);
    }

    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
