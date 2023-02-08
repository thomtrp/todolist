package 1.1.0;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class todolistApplication extends Application<todolistConfiguration> {

    public static void main(final String[] args) throws Exception {
        new todolistApplication().run(args);
    }

    @Override
    public String getName() {
        return "todolist";
    }

    @Override
    public void initialize(final Bootstrap<todolistConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final todolistConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
