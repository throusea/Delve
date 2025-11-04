using System;
using Unity.VisualScripting;
using UnityEditor;
using UnityEngine;
using UnityEngine.InputSystem;
using UnityEngine.UIElements;

public class BattleManager: MonoBehaviour
{
    private enum BattleState
    {
        WaitingForSelection, AwaitingTarget, ExecutingAction
    }

    private BattleState state;

    public UnitManager unitManager;

    public GameObject aimArrowPrefab;

    private GameObject _currentArrowInstance;

    private UnitController _selectdHero;

    void Awake()
    {
        state = BattleState.WaitingForSelection;
        if (unitManager != null)
        {
            unitManager.OnUnitSelectedEvent += HandleUnitSelected;
        }
    }

    public void HandleUnitSelected(UnitController unit)
    {
        if (state == BattleState.WaitingForSelection)
        {
            state = BattleState.AwaitingTarget;
            StartAiming(unit);
        }
        else if (state == BattleState.AwaitingTarget)
        {
            state = BattleState.WaitingForSelection;
            StopAiming();
        }
    }

    public void HandleEnemySelected(EnemyController enemy)
    {
        if (state == BattleState.AwaitingTarget)
        {
            //TODO: 执行战斗！
        }
    }

    public void StartAiming(UnitController unit)
    {
        // 创建箭头预制体
        _selectdHero = unit;
        _currentArrowInstance = Instantiate(aimArrowPrefab);
        _currentArrowInstance.GetComponent<LineRenderer>().SetPosition(0, unit.GetComponentInParent<RectTransform>().position);
        Debug.Log("创建箭头！");
    }

    public void StopAiming()
    {
        // 销毁箭头预制体
        if (_currentArrowInstance != null)
        {
            Destroy(_currentArrowInstance);
            _currentArrowInstance = null;
        }
        _selectdHero = null;
        Debug.Log("销毁箭头！");
    }

    private void UpdateArrowEndpoint(Vector3 position)
    {
        _currentArrowInstance.GetComponent<LineRenderer>().SetPosition(1, position);
    }

    void Update()
    {
        if (state == BattleState.AwaitingTarget && _currentArrowInstance != null)
        {
            Mouse mouse = Mouse.current;
            if (mouse != null)
            {
                Ray ray = Camera.main.ScreenPointToRay(mouse.position.ReadValue());

                Plane groundPlane = new Plane(Vector3.forward, 0); // 假定战斗在 Z=0 平面上
                float distance;
                if (groundPlane.Raycast(ray, out distance))
                {
                    Vector3 targetPosition = ray.GetPoint(distance);
                    UpdateArrowEndpoint(targetPosition);
                }
            }
        }
    }

    void OnDestroy()
    {
        if (unitManager != null)
        {
            unitManager.OnUnitSelectedEvent -= HandleUnitSelected;
        }
    }
}