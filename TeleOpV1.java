package org.firstinspires.ftc.teamcode.biobuzz;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TeleOp")
public class TeleOpV1 extends OpMode {
    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private DcMotor flywheel, intake, arm;

    private double driveSpeed = 1.0;

    @Override
    public void init() {
        //Initialize wheel motors, assuming that the names will be setup the same way as last year.
        leftFront = hardwareMap.get(DcMotor.class, "LF");
        rightFront = hardwareMap.get(DcMotor.class, "RF");
        leftBack = hardwareMap.get(DcMotor.class, "LB");
        rightBack = hardwareMap.get(DcMotor.class, "RB");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

        //Initialize flywheel motor, intake, and arm (assuming we are going to use a flywheel for our shooting mechanism)
        flywheel = hardwareMap.get(DcMotor.class, "flywheel");
        intake = hardwareMap.get(DcMotor.class, "intake");
        arm = hardwareMap.get(DcMotor.class, "arm");
    }

    @Override
    public void loop() {
        //Gamepad 1 sets speeds for the drive motors.
        if (gamepad1.left_trigger > 0.3) {
            driveSpeed = 0.35;
        } else if (gamepad1.right_trigger > 0.3) {
            driveSpeed = 1.0;
        } else {
            driveSpeed = 0.75;
        }

        //Math to allow gamepad to move motors.
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);

        double frontLeftPower  = ((y + x + rx) / denominator) * driveSpeed;
        double backLeftPower   = ((y - x + rx) / denominator) * driveSpeed;
        double frontRightPower = ((y - x - rx) / denominator) * driveSpeed;
        double backRightPower  = ((y + x - rx) / denominator) * driveSpeed;

        leftFront.setPower(frontLeftPower);
        leftBack.setPower(backLeftPower);
        rightFront.setPower(frontRightPower);
        rightBack.setPower(backRightPower);

        //Gamepad 2 to use flywheel
        if (gamepad2.a) {
            flywheel.setPower(1.0);
        } else {
            flywheel.setPower(0.0);
        }

        //Gamepad 2 intake
        if (gamepad2.right_bumper) {
            intake.setPower(1.0);
        } else if (gamepad2.left_bumper) {
            intake.setPower(-1.0);
        } else {
            intake.setPower(0.0);
        }

        arm.setPower(-gamepad2.right_stick_y * 0.5);
    }
}