package pid;

@FunctionalInterface
public interface PIDOutput {

	void pidWrite(double error);

}
