using System;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;


public class UnitManager: MonoBehaviour
{
    public DiceManager diceManager;

    public Action<UnitController> OnUnitSelectedEvent;
    public List<UnitController> unitCtrls;

    void Awake()
    {
        // register
        if (diceManager != null)
        {
            diceManager.OnDiceSelectionChangedEvent += HandleDiceSelectionChange;
        }
    }

    public void HandleDiceSelectionChange(List<int> dices)
    {
        Skill skill = CombinationChecker.CheckCombination(dices);
        foreach (UnitController unitCtrl in unitCtrls)
        {
            // send to unit controller
            unitCtrl.HandleDiceSelectionChange(skill);
        }
    }

    void OnDestroy()
    {
        if (diceManager != null)
        {
            diceManager.OnDiceSelectionChangedEvent -= HandleDiceSelectionChange;
        }
    }

    public void NotifyUnitSelected(UnitController unit)
    {
        OnUnitSelectedEvent?.Invoke(unit);
    }

    public void OnUnitClicked(UnitController unit)
    {
        // 广播被选中事件
        NotifyUnitSelected(unit);
    }
}