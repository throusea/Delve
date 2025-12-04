using System.Collections.Generic;
using UnityEngine;

[CreateAssetMenu(fileName = "New Skill Data", menuName = "Mechanics/Skill Data")]
public class SkillData : ScriptableObject
{
    // Skill data and properties can be defined here in the future
    public string skillName = "New Skill";

    public int maxUses = 1; // 释放次数
    public int baseDamage = 1; // 基础伤害点数

    public int baseHeal = 0; // 基础治疗点数
    public TargetType targetType = TargetType.Enemy; // 伤害目标类型

    public virtual void Initialize()
    {
        // 初始化逻辑
    }

    public virtual void ResetSkill()
    {
        // 重置技能状态
    }

    public virtual bool CanBeReleased()
    {
        return false;
    }

    // 可选：一个公共的执行方法，由 BattleController 调用
    public virtual void Execute(UnitController caster, UnitController target)
    {
        // 默认执行逻辑
        Debug.Log($"[{skillName}] 造成 {baseDamage} 基础伤害。");
    }
}

public enum RequiredDicesType
{
    Single,
    RequiresTarget,
    RequiresAllyTarget,
    RequiresEnemyTarget
}

public interface IRequiredDicesType
{
    bool ValidateSelection(List<DiceUI> selectedDices, UnitController caster, UnitController target = null);
}

public enum TargetType
{
    Enemy,
    Ally,
    AllEnemies,
    AllAllies
}