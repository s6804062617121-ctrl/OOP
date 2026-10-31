const $ = (id) => document.getElementById(id);

class Problem {
    constructor(method, values) {
        this.method = method;
        this.values = values;
    }

    get(key) {
        return this.values[key];
    }

    number(key) {
        const value = Number(this.get(key));
        if (!Number.isFinite(value)) {
            throw new Error("กรุณากรอกตัวเลขให้ถูกต้อง");
        }
        return value;
    }
}

class NumericalMethodSolver {
    show(message, explanation) {
        $("result").textContent = message;
        $("explain").textContent = explanation;
    }

    evaluate(expression, x) {
        let source = String(expression)
            .replace(/\^/g, "**")
            .replace(/\bsin\b/g, "Math.sin")
            .replace(/\bcos\b/g, "Math.cos")
            .replace(/\btan\b/g, "Math.tan")
            .replace(/\bexp\b/g, "Math.exp")
            .replace(/\blog\b/g, "Math.log")
            .replace(/\bsqrt\b/g, "Math.sqrt")
            .replace(/\babs\b/g, "Math.abs");

        if (!/^[0-9xX+\-*/().,\s*Mathsincotaepglqrtb]*$/.test(source)) {
            throw new Error("รูปแบบฟังก์ชันไม่รองรับ");
        }

        const value = Function(
            "x",
            '"use strict"; return (' + source.replace(/\bX\b/g, "x") + ");"
        )(x);

        if (!Number.isFinite(value)) {
            throw new Error("ผลคำนวณไม่ใช่จำนวนที่ถูกต้อง");
        }
        return value;
    }

    solve(problem) {
        try {
            switch (problem.method) {
                case "root":
                    return this.bisection(problem);
                case "linear":
                    return this.gaussian(problem);
                case "interp":
                    return this.lagrangeProblem(problem, false);
                case "extra":
                    return this.lagrangeProblem(problem, true);
                case "integral":
                    return this.trapezoidal(problem);
                case "diff":
                    return this.centralDifference(problem);
                default:
                    throw new Error("ไม่พบวิธีคำนวณที่เลือก");
            }
        } catch (error) {
            this.show(
                "เกิดข้อผิดพลาด: " + error.message,
                "ตรวจสอบข้อมูลที่กรอกแล้วลองใหม่อีกครั้ง"
            );
        }
    }

    bisection(problem) {
        const f = (x) => this.evaluate(problem.get("fn"), x);
        let a = problem.number("a");
        let b = problem.number("b");
        const tolerance = problem.number("tol");

        if (!(a < b)) throw new Error("ต้องกำหนด a น้อยกว่า b");
        if (!(tolerance > 0)) throw new Error("ค่าความคลาดเคลื่อนต้องมากกว่า 0");

        let fa = f(a);
        let fb = f(b);

        if (fa === 0) {
            return this.show("ราก = " + a, "พบรากที่ขอบเขต a");
        }
        if (fb === 0) {
            return this.show("ราก = " + b, "พบรากที่ขอบเขต b");
        }
        if (fa * fb > 0) {
            throw new Error("f(a) และ f(b) ต้องมีเครื่องหมายต่างกัน");
        }

        let c = a;
        let iteration = 0;

        while (Math.abs(b - a) > tolerance && iteration < 1000) {
            c = (a + b) / 2;
            const fc = f(c);

            if (fc === 0) {
                a = c;
                b = c;
                break;
            }
            if (fa * fc < 0) {
                b = c;
                fb = fc;
            } else {
                a = c;
                fa = fc;
            }
            iteration++;
        }

        c = (a + b) / 2;
        this.show(
            "รากประมาณ = " + c +
            "\nf(x) = " + f(c) +
            "\nจำนวนรอบ = " + iteration,
            "Bisection แบ่งช่วง [a,b] ครึ่งหนึ่งทุกครั้ง แล้วเลือกช่วงที่ยังคร่อมรากจนความคลาดเคลื่อนต่ำกว่าค่าที่กำหนด"
        );
    }

    gaussian(problem) {
        const matrix = problem.get("matrix").trim();
        if (!matrix) throw new Error("กรุณากรอกเมทริกซ์เสริม");

        const rows = matrix.split(/\n/).map((row) =>
            row.trim().split(/[,\s]+/).map(Number)
        );
        const n = rows.length;

        if (!n || rows.some((row) =>
            row.length !== n + 1 || row.some((value) => !Number.isFinite(value))
        )) {
            throw new Error("จำนวนคอลัมน์ต้องเท่ากับจำนวนแถว + 1 และกรอกตัวเลขให้ครบ");
        }

        for (let k = 0; k < n; k++) {
            let pivot = k;
            for (let i = k + 1; i < n; i++) {
                if (Math.abs(rows[i][k]) > Math.abs(rows[pivot][k])) {
                    pivot = i;
                }
            }

            [rows[k], rows[pivot]] = [rows[pivot], rows[k]];
            if (Math.abs(rows[k][k]) < 1e-12) {
                throw new Error("เมทริกซ์ไม่มีคำตอบเฉพาะหนึ่งชุด (pivot เป็นศูนย์)");
            }

            const divisor = rows[k][k];
            for (let j = k; j <= n; j++) rows[k][j] /= divisor;

            for (let i = 0; i < n; i++) {
                if (i === k) continue;
                const factor = rows[i][k];
                for (let j = k; j <= n; j++) {
                    rows[i][j] -= factor * rows[k][j];
                }
            }
        }

        this.show(
            rows.map((row, index) => "x" + (index + 1) + " = " + row[n]).join("\n"),
            "ใช้ Gaussian elimination พร้อม partial pivoting เพื่อกำจัดตัวแปรในแต่ละแถว แล้วอ่านคำตอบจากคอลัมน์สุดท้าย"
        );
    }

    points(text) {
        if (!text.trim()) throw new Error("กรุณากรอกจุดข้อมูล");

        const rows = text.trim().split(/\n/).map((row) =>
            row.trim().split(/[,\s]+/).map(Number)
        );

        if (rows.length < 2 || rows.some((row) =>
            row.length !== 2 || row.some((value) => !Number.isFinite(value))
        )) {
            throw new Error("กรอกจุดข้อมูลอย่างน้อย 2 จุด ในรูปแบบ x,y");
        }

        const xValues = rows.map((row) => row[0]);
        if (new Set(xValues).size !== xValues.length) {
            throw new Error("ค่า x ของจุดข้อมูลต้องไม่ซ้ำ");
        }
        return rows;
    }

    lagrange(points, x) {
        let result = 0;
        for (let i = 0; i < points.length; i++) {
            let term = points[i][1];
            for (let j = 0; j < points.length; j++) {
                if (i !== j) {
                    term *= (x - points[j][0]) /
                        (points[i][0] - points[j][0]);
                }
            }
            result += term;
        }
        return result;
    }

    lagrangeProblem(problem, outside) {
        const points = this.points(problem.get("points"));
        const x = problem.number("target");
        const min = Math.min(...points.map((point) => point[0]));
        const max = Math.max(...points.map((point) => point[0]));

        if (outside && x >= min && x <= max) {
            throw new Error("กรุณาเลือก x ที่อยู่นอกช่วงข้อมูล");
        }
        if (!outside && (x < min || x > max)) {
            throw new Error("x ที่ต้องการควรอยู่ภายในช่วงข้อมูล");
        }

        this.show(
            "y ประมาณ = " + this.lagrange(points, x),
            "ใช้ Lagrange polynomial สร้างพหุนามจากจุดข้อมูลที่กำหนด แล้วแทนค่า x เป้าหมาย"
        );
    }

    trapezoidal(problem) {
        const f = (x) => this.evaluate(problem.get("fn"), x);
        const a = problem.number("a");
        const b = problem.number("b");
        const n = problem.number("n");

        if (!Number.isInteger(n) || n < 1 || n > 100000) {
            throw new Error("n ต้องเป็นจำนวนเต็มระหว่าง 1 ถึง 100000");
        }

        const h = (b - a) / n;
        let sum = (f(a) + f(b)) / 2;
        for (let i = 1; i < n; i++) sum += f(a + i * h);

        this.show(
            "ค่าปริพันธ์ประมาณ = " + (h * sum) + "\nจำนวนช่วง n = " + n,
            "กฎคางหมูใช้พื้นที่รูปสี่เหลี่ยมคางหมูย่อยรวมกันเพื่อประมาณพื้นที่ใต้กราฟ"
        );
    }

    centralDifference(problem) {
        const f = (x) => this.evaluate(problem.get("fn"), x);
        const x = problem.number("x");
        const h = problem.number("h");
        if (!(h > 0)) throw new Error("h ต้องมากกว่า 0");

        const derivative = (f(x + h) - f(x - h)) / (2 * h);
        this.show(
            "อนุพันธ์ประมาณ f'(x) = " + derivative + "\nที่ x = " + x,
            "Central Difference คำนวณจาก [f(x+h)-f(x-h)]/(2h) โดยเลือก h ให้เหมาะสม"
        );
    }
}

const solver = new NumericalMethodSolver();

function runProblem(method, fields) {
    const values = {};
    for (const [key, elementId] of fields) {
        values[key] = $(elementId).value;
    }
    solver.solve(new Problem(method, values));
}

function solveRoot() {
    runProblem("root", [
        ["fn", "rootFn"], ["a", "rootA"], ["b", "rootB"], ["tol", "rootTol"]
    ]);
}

function solveLinear() {
    runProblem("linear", [["matrix", "matrix"]]);
}

function solveInterp(outside) {
    runProblem(outside ? "extra" : "interp", [
        ["points", outside ? "points2" : "points"],
        ["target", outside ? "target2" : "target"]
    ]);
}

function solveIntegral() {
    runProblem("integral", [
        ["fn", "intFn"], ["a", "intA"], ["b", "intB"], ["n", "intN"]
    ]);
}

function solveDiff() {
    runProblem("diff", [
        ["fn", "diffFn"], ["x", "diffX"], ["h", "diffH"]
    ]);
}

document.querySelectorAll("nav button").forEach((button) => {
    button.addEventListener("click", () => {
        document.querySelectorAll("nav button").forEach((item) =>
            item.classList.remove("active")
        );
        document.querySelectorAll(".method").forEach((item) =>
            item.classList.remove("active")
        );

        button.classList.add("active");
        $(button.dataset.tab).classList.add("active");
        solver.show(
            "กรอกข้อมูลแล้วกดปุ่มคำนวณ",
            "ผลลัพธ์จะแสดงที่นี่"
        );
    });
});
