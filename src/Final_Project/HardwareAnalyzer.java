package Final_Project;

public class HardwareAnalyzer {

    public static String compare(HardwareComponent h1, HardwareComponent h2) {
        if (h1 == null || h2 == null) return "Component(s) not found.";
        if (!h1.getType().equalsIgnoreCase(h2.getType())) return "Cannot compare different types (CPU vs GPU).";

        String result = "Comparing " + h1.getName() + " vs. " + h2.getName() + ":\n";

        double clockDiffRaw = Math.abs(h1.getClockSpeed() - h2.getClockSpeed());
        String clockDiff = String.format("%.1f", clockDiffRaw);
        int cacheDiff = Math.abs(h1.getCache() - h2.getCache());
        int powerDiff = Math.abs(h1.getPower() - h2.getPower());

        // Clock speed comparison
        if (h1.getClockSpeed() > h2.getClockSpeed()) {
            result += h1.getName() + " has higher clock speed (+" + clockDiff + " GHz).\n";
        } else if (h1.getClockSpeed() < h2.getClockSpeed()) {
            result += h2.getName() + " has higher clock speed (+" + clockDiff + " GHz).\n";
        } else {
            result += "Both have the same clock speed.\n";
        }

        // Cache comparison
        if (h1.getCache() > h2.getCache()) {
            result += h1.getName() + " has more cache (+" + cacheDiff + " MB).\n";
        } else if (h1.getCache() < h2.getCache()) {
            result += h2.getName() + " has more cache (+" + cacheDiff + " MB).\n";
        } else {
            result += "Both have the same cache.\n";
        }

        // Power comparison
        if (h1.getPower() < h2.getPower()) {
            result += h1.getName() + " is more power-efficient (−" + powerDiff + " W).\n";
        } else if (h1.getPower() > h2.getPower()) {
            result += h2.getName() + " is more power-efficient (−" + powerDiff + " W).\n";
        } else {
            result += "Both have the same power consumption.\n";
        }

        // Simple performance score
        double score1 = h1.getClockSpeed() * h1.getCache();
        double score2 = h2.getClockSpeed() * h2.getCache();

        String formatted1 = String.format("%.1f", score1);
        String formatted2 = String.format("%.1f", score2);

        if (score1 > score2) {
            result += h1.getName() + " is overall better (Score: " + formatted1 + " vs. " + formatted2 + ").\n";
        } else if (score2 > score1) {
            result += h2.getName() + " is overall better (Score: " + formatted2 + " vs. " + formatted1 + ").\n";
        } else {
            result += "Both have the same overall score (" + formatted1 + ").\n";
        }

        return result;
    }

    public static String suggestUpgrade(HardwareComponent cpu, HardwareComponent gpu) {
        if (cpu == null || gpu == null) {
            return "CPU or GPU not found.";
        }

        double cpuScore = cpu.getClockSpeed() * cpu.getCache();
        double gpuScore = gpu.getClockSpeed() * gpu.getCache();

        if (cpuScore > gpuScore * 1.3) {
            return "Your GPU is underpowered compared to your CPU. Upgrade GPU.";
        } else if (gpuScore > cpuScore * 1.3) {
            return "Your CPU is underpowered compared to your GPU. Upgrade CPU.";
        } else {
            return "Your system is balanced.";
        }
    }
}
