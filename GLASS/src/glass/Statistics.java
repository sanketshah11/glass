/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glass;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.jfree.data.xy.XYSeriesCollection;

public class Statistics
{

public static Double keyList[] = new Double[5];
public static Double valueList[] = new Double[5];

public static double minimumBound(double[] inputArray)
{

    double min = Double.MAX_VALUE;
    for (int i = 0; i < inputArray.length; i++)
    {
        min = minIgnoreNaN(min, inputArray[i]);
    }
    return min;
}

public static double maximumBound(double[] inputArray)
{

    double max = Double.NEGATIVE_INFINITY;
    for (int i = 0; i < inputArray.length; i++)
    {
        max = maxIgnoreNaN(max, inputArray[i]);
    }
    return max;
}

public static double maxIgnoreNaN(double a, double b)
{
    if (Double.isNaN(a))
    {
        return b;
    }// end method max
    else
    {
        if (Double.isNaN(b))
        {
            return a;
        }
        else
        {
            return Math.max(a, b);
        }
    }
}

public static double minIgnoreNaN(double a, double b)
{
    if (Double.isNaN(a))
    {
        return b;
    }
    else
    {
        if (Double.isNaN(b))
        {
            return a;
        }
        else
        {
            return Math.min(a, b);
        }
    }
}// end method min

public static double sum(double[] x_i)
{
    int i = 0;
    double total = 0;
    for (i = 0; i < x_i.length; i++)
    {
        if (Double.isNaN(x_i[i]) == false)
        {
            total = (total + x_i[i]);
        }
    }
//		Debug.Print("Inside sum "+total);
    return total;
}

public static double standarddeviation(XYSeriesCollection seriesCollection[],
                                       int graphLocation,
                                       double xlowerBound,
                                       double ylowerBound,
                                       double xupperBound,
                                       double yupperBound,
                                       double meanValue[])
{

    double std = 0, total = 0;
    double intermediateValue = 0;
    double pow_by_n = 0;
    int LineCnt;
    int histSeries = 0;
    int seriesCount = seriesCollection[graphLocation].getSeriesCount();
    for (int k = 0; k < seriesCount; k++)
    {
        total = 0;
        LineCnt = 0;
        LineCnt = seriesCollection[graphLocation].getSeries(k).getItemCount();

        for (int i = 0; i < LineCnt; i++)
        {
            if ((xlowerBound < seriesCollection[graphLocation].getSeries(k).getX(i).doubleValue())
                && (seriesCollection[graphLocation].getSeries(k).getX(i).doubleValue() < xupperBound)
                && (ylowerBound < seriesCollection[graphLocation].getSeries(k).getY(i).doubleValue())
                && (seriesCollection[graphLocation].getSeries(k).getY(i).doubleValue() < yupperBound))
            {
                intermediateValue = seriesCollection[graphLocation].getSeries(k).getY(i).doubleValue() - meanValue[k];
                total = total + (intermediateValue * intermediateValue);
            }
        }
        if (LineCnt != 0)
        {
            pow_by_n = total / LineCnt;
        }
        else
        {
            pow_by_n = 0;
        }
        std = Math.sqrt(pow_by_n);

    }
    return std;
}

public static void mode(double a[])
{
    keyList = new Double[5];
    valueList = new Double[5];

    Map<Double, Double> map = new HashMap<>();
    for (double i : a)
    {
        Double count = map.get(i);
        map.put(i, count != null ? count + 1 : 0);
    }
    keyList[0] = Collections.max(map.entrySet(),
                                 new Comparator<Map.Entry<Double, Double>>()
                                 {
                                 @Override
                                 public int compare(Entry<Double, Double> o1, Entry<Double, Double> o2)
                                 {
                                     return o1.getValue().compareTo(o2.getValue());
                                 }

                                 }).getKey();
    valueList[0] = map.get(keyList[0]);
    map.remove(keyList[0]);

    keyList[1] = Collections.max(map.entrySet(),
                                 new Comparator<Map.Entry<Double, Double>>()
                                 {
                                 @Override
                                 public int compare(Entry<Double, Double> o1, Entry<Double, Double> o2)
                                 {
                                     return o1.getValue().compareTo(o2.getValue());
                                 }

                                 }).getKey();
    valueList[1] = map.get(keyList[1]);
    map.remove(keyList[1]);

    keyList[2] = Collections.max(map.entrySet(),
                                 new Comparator<Map.Entry<Double, Double>>()
                                 {
                                 @Override
                                 public int compare(Entry<Double, Double> o1, Entry<Double, Double> o2)
                                 {
                                     return o1.getValue().compareTo(o2.getValue());
                                 }

                                 }).getKey();
    valueList[2] = map.get(keyList[2]);
    map.remove(keyList[2]);

    keyList[3] = Collections.max(map.entrySet(),
                                 new Comparator<Map.Entry<Double, Double>>()
                                 {
                                 @Override
                                 public int compare(Entry<Double, Double> o1, Entry<Double, Double> o2)
                                 {
                                     return o1.getValue().compareTo(o2.getValue());
                                 }

                                 }).getKey();
    valueList[3] = map.get(keyList[3]);
    map.remove(keyList[3]);

    keyList[4] = Collections.max(map.entrySet(),
                                 new Comparator<Map.Entry<Double, Double>>()
                                 {
                                 @Override
                                 public int compare(Entry<Double, Double> o1, Entry<Double, Double> o2)
                                 {
                                     return o1.getValue().compareTo(o2.getValue());
                                 }

                                 }).getKey();
    valueList[4] = map.get(keyList[4]);
    map.remove(keyList[4]);
}

}
