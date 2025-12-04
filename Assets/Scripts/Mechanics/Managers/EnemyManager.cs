using System;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;


public class EnemyManager: MonoBehaviour
{
    public DiceManager diceManager;

    public Action<EnemyController> OnEnemySelectedEvent;
    public List<EnemyController> enemyCtrls;

    void Awake()
    {
        // register
        if (diceManager != null)
        {
            //
        }
    }

    public void HandleDiceSelectionChange(List<int> dices)
    {
        // FIXME: 敌人不需要响应骰子变化
    }

    void OnDestroy()
    {
        if (diceManager != null)
        {
            diceManager.OnDiceSelectionChangedEvent -= HandleDiceSelectionChange;
        }
    }

    public void NotifyEnemySelected(EnemyController enemy)
    {
        OnEnemySelectedEvent?.Invoke(enemy);
    }

    public void OnEnemyClicked(EnemyController enemy)
    {
        // 广播被选中事件
        NotifyEnemySelected(enemy);
    }
}