package com.linkedin.thirdeye.dashboard.resources.v2.pojo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.linkedin.thirdeye.api.DimensionMap;
import com.linkedin.thirdeye.datalayer.dto.MergedAnomalyResultDTO;

public class SearchFilters {

  TreeMap<String, Integer> statusFilterMap;

  TreeMap<String, Integer> datasetFilterMap;

  TreeMap<String, Integer> metricFilterMap;

  Map<String, Map<String, Integer>> dimensionFilterMap;

  public SearchFilters(TreeMap<String, Integer> statusFilterMap, TreeMap<String, Integer> datasetFilterMap, TreeMap<String, Integer> metricFilterMap,
      Map<String, Map<String, Integer>> dimensionFilterMap) {
    super();
    this.statusFilterMap = statusFilterMap;
    this.datasetFilterMap = datasetFilterMap;
    this.metricFilterMap = metricFilterMap;
    this.dimensionFilterMap = dimensionFilterMap;
  }

  public static SearchFilters fromAnomalies(List<MergedAnomalyResultDTO> mergedAnomalies) {
    TreeMap<String, Integer> statusFilterMap = new TreeMap<>();
    TreeMap<String, Integer> datasetFilterMap = new TreeMap<>();
    TreeMap<String, Integer> metricFilterMap = new TreeMap<>();
    Map<String, Map<String, Integer>> dimensionFilterMap = new TreeMap<>();

    for (MergedAnomalyResultDTO mergedAnomalyResultDTO : mergedAnomalies) {
      // update status filter
      String status = mergedAnomalyResultDTO.getFeedback().getStatus().toString();
      update(statusFilterMap, status);
      // update datasetFilterMap
      String dataset = mergedAnomalyResultDTO.getCollection();
      update(datasetFilterMap, dataset);
      // update metricFilterMap
      String metric = mergedAnomalyResultDTO.getMetric();
      update(metricFilterMap, metric);
      // update dimension
      DimensionMap dimensions = mergedAnomalyResultDTO.getDimensions();
      for (String dimensionName : dimensions.keySet()) {
        if (!dimensionFilterMap.containsKey(dimensionName)) {
          dimensionFilterMap.put(dimensionName, new TreeMap<String, Integer>());
        }
        String dimensionValue = dimensions.get(dimensionName);
        update(dimensionFilterMap.get(dimensionName), dimensionValue);
      }
    }
    return new SearchFilters(statusFilterMap, datasetFilterMap, metricFilterMap, dimensionFilterMap);
  }

  private static void update(Map<String, Integer> map, String value) {
    Integer count = map.get(value);
    if (count == null) {
      map.put(value, 1);
    } else {
      map.put(value, count + 1);
    }
  }

}
