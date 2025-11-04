using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.InputSystem;
using UnityEngine.UI;

public class UnitController : MonoBehaviour
{
    public UnitManager manager;
    public int health;

    public List<Skill> skillPool;  // 可用技能池

    public SkillController skillCtrl;

    public Image highlightImage;  // 高亮状态
    Slider healthSlider; // 生命值UI

    TextMeshProUGUI nameText;

    public DelveControls inputCtrl;

    void Awake()
    {
        inputCtrl = new DelveControls();
        nameText = GetComponentInChildren<TextMeshProUGUI>();
        healthSlider = GetComponentInChildren<Slider>();
        healthSlider.maxValue = 5;
        healthSlider.minValue = 0;
        healthSlider.value = 5;

        highlightImage.color = Color.red; // 初始化状态
    }
    
    void OnEnable()
    {
        inputCtrl.GamePlay.PrimaryTouch.performed += OnUnitClick;
        inputCtrl.Enable();
    }

    void OnDisable()
    {
        inputCtrl.GamePlay.PrimaryTouch.performed -= OnUnitClick;
        inputCtrl.Disable();
    }

    /// <summary>
    /// 更新血条
    /// </summary>
    public void UpdateHealthSlider()
    {
        healthSlider.value = health;
    }

    // 当骰子被选中状态发生变化时触发
    public void HandleDiceSelectionChange(Skill skill)
    {
        // 判断角色是否产生高亮状态
        if (skillPool.Contains(skill))
        {
            highlightImage.color = Color.yellow;
        }
        else
        {
            highlightImage.color = Color.red;
        }

        // 对每个角色更新现有技能和技能按钮文本
        skillCtrl.SetAvailiableSkill(skill);
        // skillCtrl.UpdateButtonText();
        Debug.Log("更新角色可用技能" + $"{skill}");
    }

    public void OnUnitClick(InputAction.CallbackContext context)
    {
        Vector2 pos = context.ReadValue<Vector2>();
        Vector3 worldPos;
        InputToWorldConverter.TryGetMouseWorldPosition(out worldPos);

        Debug.Log($"{worldPos}");

        RectTransform rect = GetComponent<RectTransform>();
    
        if (InputToWorldConverter.RectContainsScreenMousePosition(rect))
        {
            Debug.Log($"角色被选中！ - {pos}");
            manager.OnUnitClicked(this);
        }
    }

}