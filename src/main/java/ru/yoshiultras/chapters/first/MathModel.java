package ru.yoshiultras.chapters.first;

public class MathModel {
    private double k;
    private double n;
    private double fi;
    private double lambda;

    public MathModel(double k, double n, double fi, double lambda) {
        this.k = k;
        this.n = n;
        this.fi = fi;
        this.lambda = lambda;
    }

    public double p() {
        return lambda/fi;
    }

    public double ps() {
        return p()/k;
    }

    public double p0() {
        double res = 0;
        double p = p();
        double ps = ps();
        for (int i = 0; i < k; i++) {
            res += Math.pow(p, i) / factorial(i);
        }
        res += (Math.pow(p, k) * (1 - Math.pow(ps, n+1))) / (factorial(k) * (1 - ps));
        res = Math.floor(res);
        return Math.pow(res, -1);
    }

    public double pden() {
        return (Math.pow(p(), k+n) * p0()) / (factorial(k) * Math.pow(k, n));
    }

    public double kch() {
        return p() * (1 - pden());
    }

    public double wreq() {
        return ((Math.pow(p(), k+1) * p0()) / (factorial(k) * k)) *
                Math.floor((1 - Math.pow(ps(), n) * (n + 1 - n * ps()))/(Math.pow(1-ps(), 2)));
    }

    public double treq() {
        return wreq() / lambda;
    }

    public double ws() {
        return kch() + wreq();
    }

    public double ts() {
        return treq() + (1 - pden()) / fi;
    }

    public void print() {
        System.out.printf("Входные параметры: k=%.0f, n=%.0f, µ=%.0f, λ=%.0f\n", k, n, fi, lambda);
        System.out.printf("Коэффициент разгрузки р = %.4f\n", p());
        System.out.printf("Коэффициент загрузки СМО ps = %.4f\n", ps());
        System.out.println("Показатель простоя р0 = " + p0());
        System.out.printf("Вероятность отказа в обслуживании pden = %.4f\n", pden());
        System.out.printf("Среднее число занятых каналов kch = %.4f\n", kch());
        System.out.printf("Среднее число запросов в очереди Wreq = %.4f\n", wreq());
        System.out.printf("Среднее время ожидания запроса в очереди Treq = %.4f\n", treq());
        System.out.printf("Среднее число запросов в СМО Ws = %.4f\n", ws());
        System.out.printf("Среднее время пребывания запроса в СМО Ts = %.4f\n", ts());
    }

    private long factorial(double n) {
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }

    public static void main(String[] args) {
        MathModel model1 = new MathModel(4, 3, 17, 200);
        MathModel model2 = new MathModel(4, 3, 10, 50);
        MathModel model3 = new MathModel(4, 5, 10, 200);
        System.out.println("Эксперимент №1");
        model1.print();
        System.out.println("\nЭксперимент №2");
        model2.print();
        System.out.println("\nЭксперимент №3");
        model3.print();
    }
}
