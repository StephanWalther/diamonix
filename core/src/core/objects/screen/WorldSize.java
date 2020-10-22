package core.objects.screen;

public enum WorldSize {
    extraLarge {
        @Override
        public int idealWidth() {
            return 1440;
        }
        
        @Override
        public int idealHeight() {
            return 2560;
        }
        
        @Override
        public int maxWidth() {
            return 1728;
        }
        
        @Override
        public int maxHeight() {
            return 3072;
        }
    },
    
    large {
        @Override
        public int idealWidth() {
            return 1080;
        }
        
        @Override
        public int idealHeight() {
            return 1920;
        }
        
        @Override
        public int maxWidth() {
            return 1296;
        }
        
        @Override
        public int maxHeight() {
            return 2304;
        }
    },
    
    medium {
        @Override
        public int idealWidth() {
            return 720;
        }
        
        @Override
        public int idealHeight() {
            return 1280;
        }
        
        @Override
        public int maxWidth() {
            return 864;
        }
        
        @Override
        public int maxHeight() {
            return 1536;
        }
    },
    
    small {
        @Override
        public int idealWidth() {
            return 450;
        }
        
        @Override
        public int idealHeight() {
            return 800;
        }
        
        @Override
        public int maxWidth() {
            return 540;
        }
        
        @Override
        public int maxHeight() {
            return 960;
        }
    };
    
    public abstract int idealWidth();
    
    public abstract int idealHeight();
    
    public abstract int maxWidth();
    
    public abstract int maxHeight();
}
