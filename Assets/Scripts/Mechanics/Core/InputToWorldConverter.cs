using UnityEngine;
using UnityEngine.InputSystem;

public static class InputToWorldConverter
{
    // 定义默认的地面平面，可以根据需要在其他方法中覆盖
    private static readonly Plane _defaultGroundPlane = new Plane(Vector3.forward, 0);

    /// <summary>
    /// 将当前鼠标位置转换为世界空间中指定平面上的坐标。
    /// </summary>
    /// <param name="targetPlane">用于射线投射的平面。</param>
    /// <param name="worldPosition">如果成功，返回计算出的世界坐标。</param>
    /// <returns>如果射线成功击中平面，返回 True。</returns>
    public static bool TryGetMouseWorldPositionOnPlane(Plane targetPlane, out Vector3 worldPosition)
    {
        worldPosition = Vector3.zero;
        Mouse mouse = Mouse.current;

        if (mouse != null)
        {
            // 1. 从摄像机发射射线
            Ray ray = Camera.main.ScreenPointToRay(mouse.position.ReadValue());
            float distance;

            // 2. 对指定平面进行射线投射
            if (targetPlane.Raycast(ray, out distance))
            {
                // 3. 计算击中点
                worldPosition = ray.GetPoint(distance);
                return true;
            }
        }

        return false;
    }

    // 简易版本：使用默认的 Z=0 平面
    public static bool TryGetMouseWorldPosition(out Vector3 worldPosition)
    {
        return TryGetMouseWorldPositionOnPlane(_defaultGroundPlane, out worldPosition);
    }
    
    /// <summary>
    /// 判断当前矩形框是否在鼠标点击范围内（高度集成方法）
    /// </summary>
    /// <param name="rect"></param>
    /// <returns></returns>
    public static bool RectContainsScreenMousePosition(RectTransform rect)
    {
        Vector3 vector;
        TryGetMouseWorldPosition(out vector);

        Vector3 leftTop, rightTop, leftBottom;
        leftTop = rect.position + new Vector3(-0.5f * rect.localScale.x, 0.5f * rect.localScale.y);
        rightTop = rect.position + new Vector3(0.5f * rect.localScale.x, 0.5f * rect.localScale.y);
        leftBottom = rect.position + new Vector3(-0.5f * rect.localScale.x, -0.5f * rect.localScale.y);
        // rightBottom = rect.position + new Vector3(0.5f * rect.localScale.x, -0.5f * rect.localScale.y);

        if (vector.y > leftTop.y || vector.y < leftBottom.y)
            return false;
        if (vector.x < leftTop.x || vector.x > rightTop.x)
            return false;
        return true;
    }
}