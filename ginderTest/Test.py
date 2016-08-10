# Test.py
#
# A minimal script that tests The Grinder logging facility.
#
# This script shows the recommended style for scripts, with a
# TestRunner class. The script is executed just once by each worker
# process and defines the TestRunner class. The Grinder creates an
# instance of TestRunner for each worker thread, and repeatedly calls
# the instance for each run of that thread.

from net.grinder.script.Grinder import grinder
from net.grinder.script import Test
from com.jarvis import Sampler
test = Test(1, "Sample")
class TestRunner:

    # This method is called for every run.
    def __call__(self):
        mySampler = test.wrap(Sampler())
        mySampler.test()