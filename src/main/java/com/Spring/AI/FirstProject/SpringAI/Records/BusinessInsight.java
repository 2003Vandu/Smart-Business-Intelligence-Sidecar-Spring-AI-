package com.Spring.AI.FirstProject.SpringAI.Records;

import java.util.List;

public record BusinessInsight(
        String businessSummary,
        List<String> keyTrends,
        String dataGrounding,
        String suggestedAction,
        double confidenceScore
) {
    // Compact constructor - validates data
    public BusinessInsight {
        businessSummary = businessSummary != null ? businessSummary : "No summary available";
        keyTrends       = keyTrends       != null ? keyTrends       : List.of();
        dataGrounding   = dataGrounding   != null ? dataGrounding   : "Unknown source";
        suggestedAction = suggestedAction != null ? suggestedAction : "No action suggested";
        // Clamp confidence between 0 and 1
        confidenceScore = Math.max(0.0, Math.min(1.0, confidenceScore));
    }
}
//{
//    /**
//     * This record ensures the AI returns structured data that a UI can read.
//     * Interviewers love this because it shows "Type Safety."
//     */
//}
