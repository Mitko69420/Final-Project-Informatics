package Final_Project;

public class HardwareAnalyzer {

    public static String compare(HardwareComponent h1, HardwareComponent h2) {
        if (h1 == null || h2 == null) {
            return "One or both components not found.";
        }

        if (!h1.getType().equalsIgnoreCase(h2.getType())) {
            return "Cannot compare different types (CPU vs GPU).";
        }

        String result = "Comparing " + h1.getName() + " vs. " + h2.getName() + ":\n";

        if (h1.getClockSpeedGHz() > h2.getClockSpeedGHz()) {
            result += h1.getName() + " has higher clock speed.\n";
        } else if (h1.getClockSpeedGHz() < h2.getClockSpeedGHz()) {
            result += h2.getName() + " has higher clock speed.\n";
        } else {
            result += "Both have the same clock speed.\n";
        }

        if (h1.getCacheMB() > h2.getCacheMB()) {
            result += h1.getName() + " has more cache.\n";
        } else if (h1.getCacheMB() < h2.getCacheMB()) {
            result += h2.getName() + " has more cache.\n";
        } else {
            result += "Both have the same cache.\n";
        }

        if (h1.getPowerWatt() < h2.getPowerWatt()) {
            result += h1.getName() + " is more power-efficient.\n";
        } else if (h1.getPowerWatt() > h2.getPowerWatt()) {
            result += h2.getName() + " is more power-efficient.\n";
        } else {
            result += "Both have the same power consumption.\n";
        }

        return result;
    }

    public static String suggestUpgrade(HardwareComponent cpu, HardwareComponent gpu) {
        if (cpu == null || gpu == null) {
            return "CPU or GPU not found.";
        }

        double cpuScore = cpu.getClockSpeedGHz() * cpu.getCacheMB();
        double gpuScore = gpu.getClockSpeedGHz() * gpu.getCacheMB();

        if (cpuScore > gpuScore * 1.3) {
            return "Your GPU is underpowered compared to your CPU. Consider upgrading GPU.";
        } else if (gpuScore > cpuScore * 1.3) {
            return "Your CPU is underpowered compared to your GPU. Consider upgrading CPU.";
        } else {
            return "Your system is balanced.";
        }
    }
}
