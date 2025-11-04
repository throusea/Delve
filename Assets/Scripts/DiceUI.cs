using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.UI;

public class DiceUI : MonoBehaviour, IPointerClickHandler
{
    public Sprite[] diceSprites = new Sprite[6];

    private bool highlight = false;
    public Outline diceOutline;
    public Image diceImage;

    public int value;

    private DiceManager diceManager;

    public void Awake()
    {
        diceManager = FindFirstObjectByType<DiceManager>();
    }

    public bool isHighlight()
    {
        return highlight;
    }

    public void SetHighlight(bool isHighlight)
    {
        this.highlight = isHighlight;
        diceOutline.enabled = isHighlight; // 渲染亮边
    }

    public void OnPointerClick(PointerEventData eventData)
    {
        SetHighlight(!highlight);

        List<DiceUI> dices = diceManager.GetHighlightDiceUIs();
        // add highlight dice into list
        if (highlight && !dices.Contains(this))
        {
            dices.Add(this);
        }
        // delete unhighlight dice from list
        if (!highlight && dices.Contains(this))
        {
            dices.Remove(this);
        }

        // send to dice manager
        diceManager.OnDiceSelectionChanged();
    }

    public void SetValue(int value)
    {
        if (value < 1 || value > 6)
        {
            Debug.LogError("Dice value out of range: " + value);
            return;
        }

        this.value = value;
        
        diceImage.sprite = diceSprites[value - 1];
    }
    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {
        diceOutline.effectColor = Color.black;
        diceOutline.useGraphicAlpha = false;
        diceOutline.enabled = false;
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
